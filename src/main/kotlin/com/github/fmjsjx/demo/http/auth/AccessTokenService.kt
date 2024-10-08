package com.github.fmjsjx.demo.http.auth

import com.github.fmjsjx.demo.http.core.entity.Account
import com.github.fmjsjx.demo.http.core.service.InMemoryRollingCacheService
import com.github.fmjsjx.demo.http.core.service.RedisCacheService
import com.github.fmjsjx.libcommon.util.ChecksumUtil
import kotlinx.coroutines.future.await
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor

@Service
class AccessTokenService(
    private val redisCache: RedisCacheService,
    @Qualifier("accessTokenInMemoryRollingCacheService")
    private val inMemoryCache: InMemoryRollingCacheService,
) {

    companion object {

        private val logger = LoggerFactory.getLogger(AccessTokenService::class.java)

        private const val ACCESS_TOKEN_LEN = 24
        private const val ACCESS_TOKEN_TTL: Long = 86400
        private const val CONFUSION: Long = 0x4b5a3c69
        private val DIGITS: ByteArray = "0123456789abcdef".encodeToByteArray()

        private fun Long.toHexBytes(dest: ByteArray, offset: Int = 0) {
            dest[offset] = DIGITS[(this ushr 28).toInt() and 0xf]
            dest[offset + 1] = DIGITS[(this ushr 24).toInt() and 0xf]
            dest[offset + 2] = DIGITS[(this ushr 20).toInt() and 0xf]
            dest[offset + 3] = DIGITS[(this ushr 16).toInt() and 0xf]
            dest[offset + 4] = DIGITS[(this ushr 12).toInt() and 0xf]
            dest[offset + 5] = DIGITS[(this ushr 8).toInt() and 0xf]
            dest[offset + 6] = DIGITS[(this ushr 4).toInt() and 0xf]
            dest[offset + 7] = DIGITS[this.toInt() and 0xf]
        }

        private fun Long.toBytes(): ByteArray = ByteArray(8) { i -> (this shr (56 - i * 8)).toByte() }

        private fun checkCode(u: Long, unixTime: Long): Long =
            ChecksumUtil.crc32c(((unixTime shl 32) or u).toBytes())

        private fun ByteArray.parseHex(offset: Int, len: Int): Long {
            var v = 0L
            (offset..(offset + len - 1)).forEach { i ->
                val b = this[i].toInt() and 0xff
                val c = when {
                    b >= '0'.code && b <= '9'.code -> b - '0'.code
                    b >= 'a'.code && b <= 'f'.code -> b - 'a'.code + 10
                    else -> throw NumberFormatException()
                }
                v = (v shl 4) or c.toLong()
            }
            return v
        }

        private fun generateAccessTokenId(uid: Long, unixTime: Long): String {
            val b = ByteArray(ACCESS_TOKEN_LEN)
            unixTime.toHexBytes(b)
            val u = uid and 0xffffffffL
            (u xor unixTime xor CONFUSION).toHexBytes(b, 8)
            val checkCode = checkCode(u, unixTime)
            checkCode.toHexBytes(b, 16)
            return b.decodeToString().also {
                if (logger.isDebugEnabled) {
                    logger.debug(
                        "Generated access token id (uid={}, unixTime={}, checkCode={}) >>> {}",
                        uid,
                        unixTime,
                        checkCode,
                        it
                    )
                }
            }
        }

        private fun String.validateAccessTokenId(): Boolean {
            if (this.length != ACCESS_TOKEN_LEN) {
                return false
            }
            val b = encodeToByteArray()
            if (b.size != ACCESS_TOKEN_LEN) {
                return false
            }
            return b.runCatching {
                val unixTime = parseHex(0, 8)
                val ux = parseHex(8, 8)
                val checkCode = parseHex(16, 8)
                val u = (ux xor unixTime xor CONFUSION)
                checkCode == checkCode(u, unixTime)
            }.getOrDefault(false)
        }

        private fun String.toAccessTokenKey() = "auth:access-token:{$this}"

        private fun Long.toPreAccessTokenIdKey() = "auth:player:{$this}:pre-access-token:id"

    }

    suspend fun createOne(account: Account, loginInfo: LoginInfo): AccessToken {
        val accessToken = AccessTokenImpl(
            id = generateAccessTokenId(account.id, loginInfo.unixTime),
            account = account,
            remoteAddress = loginInfo.remoteAddress,
            clientVersion = loginInfo.clientVersion,
            udid = loginInfo.udid,
            deviceInfo = loginInfo.deviceInfo,
            osInfo = loginInfo.osInfo,
            loginTime = loginInfo.time,
            features = LinkedHashSet(loginInfo.features),
        )
        val preAccessTokenIdKey = accessToken.uid.toPreAccessTokenIdKey()
        // find previous access token id from redis
        redisCache.getAsync(preAccessTokenIdKey).await()?.also { preAccessTokenId ->
            // delete previous access token from redis
            redisCache.delAsync(preAccessTokenId.toAccessTokenKey())
            // remove in-memory cached access token
            inMemoryCache.removeCachedValue<AccessToken>(preAccessTokenId)
        }
        // cache access token in redis
        redisCache.setJsonAsync(accessToken.id.toAccessTokenKey(), accessToken, ACCESS_TOKEN_TTL)
        // cache current access token id as previous id in redis
        redisCache.setexAsync(preAccessTokenIdKey, ACCESS_TOKEN_TTL, accessToken.id)
        // cache access token in in-memory cache
        inMemoryCache.cacheValue(accessToken.id, accessToken)
        return accessToken
    }

    fun findOneAsync(
        id: String,
        executor: Executor,
        renewTtl: Boolean = true,
    ): CompletionStage<Optional<AccessToken>> {
        if (!id.validateAccessTokenId()) {
            return CompletableFuture.completedStage(Optional.empty())
        }
        val key = id.toAccessTokenKey()
        val cached = inMemoryCache.getCachedValue<AccessToken>(id)
        if (cached != null) {
            if (cached.isPresent && renewTtl) {
                redisCache.expireAsync(key, ACCESS_TOKEN_TTL)
                redisCache.setexAsync(cached.get().uid.toPreAccessTokenIdKey(), ACCESS_TOKEN_TTL, id)
            }
            return CompletableFuture.completedStage(cached)
        }
        return if (renewTtl) {
            redisCache.getJsonObjectAsync<AccessToken>(key, AccessTokenImpl::class.java, executor, ACCESS_TOKEN_TTL)
        } else {
            redisCache.getJsonObjectAsync<AccessToken>(key, AccessTokenImpl::class.java, executor)
        }.thenApply {
            logger.debug("cached access token: {}", it)
            it?.also { value ->
                if (value.isPresent && renewTtl) {
                    redisCache.setexAsync(value.get().uid.toPreAccessTokenIdKey(), ACCESS_TOKEN_TTL, id)
                }
            } ?: Optional.empty()
        }
    }

}
