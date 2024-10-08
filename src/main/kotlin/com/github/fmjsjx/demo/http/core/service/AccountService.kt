package com.github.fmjsjx.demo.http.core.service

import com.github.fmjsjx.demo.http.common.redis.RedisWrapped
import com.github.fmjsjx.demo.http.core.dao.AccountRepository
import com.github.fmjsjx.demo.http.core.entity.Account
import com.github.fmjsjx.demo.http.core.entity.Account.Companion.GUEST
import com.github.fmjsjx.demo.http.core.entity.Account.Companion.NORMAL
import kotlinx.coroutines.future.await
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val repository: AccountRepository,
    private val redisCache: RedisCacheService,
    private val mongodbManager: MongoDBManager,
) : RedisWrapped by redisCache {

    companion object {
        private const val KEY_GUEST_UID_MAPPINGS = "auth:guest:uid:mappings"
        private fun Long.toCacheKey() = "cache:account:{$this}"
        private fun Account.toCacheKey() = id.toCacheKey()
        private const val CACHE_TTL = 3 * 86400L
    }

    override val logger = LoggerFactory.getLogger(javaClass)!!

    suspend fun guestAccount(udid: String, newGuestAccount: () -> Account): Pair<Boolean, Account> {
        val uid = hgetAsync(KEY_GUEST_UID_MAPPINGS, udid).await()?.let { value ->
            value.runCatching {
                toLong()
            }.onFailure {
                logger.warn("[core:account] Mapped guest uid error: {} >>> {}", udid, value, it)
                hdelAsync(KEY_GUEST_UID_MAPPINGS, udid)
            }.getOrNull()
        }
        if (uid != null) {
            val account = findAccount(uid)
            if (account != null) {
                return false to account.checkGuest()
            }
            logger.warn("[core:account] Missing mapping of guest uid: {} <=> {}", udid, uid)
            hdelAsync(KEY_GUEST_UID_MAPPINGS, udid)
        }
        var newCreated = true
        var account = newGuestAccount()
        account.groupId = mongodbManager.nextGroupId()
        account.type = GUEST
        account.state = NORMAL
        account.udid = udid
        account.slot = udid.hashCode() and 0xf
        account.createdAt = System.currentTimeMillis()
        account.updatedAt = account.createdAt
        try {
            repository.insertOne(account)
        } catch (_: DuplicateKeyException) {
            account = repository.selectOneByUdid(udid)!!.checkGuest()
            newCreated = false
        }
        redisCache.setJsonAsync(account.toCacheKey(), account, CACHE_TTL)
        hsetAsync(KEY_GUEST_UID_MAPPINGS, udid, account.id.toString())
        return newCreated to account
    }

    fun Account.checkGuest(): Account = apply {
        if (type != GUEST) {
            throw IllegalStateException("Account type `$type` is not supported for guest login")
        }
    }

    suspend fun findAccount(id: Long): Account? =
        redisCache.retrieveJsonObject<Account>(id.toCacheKey(), CACHE_TTL) { repository.selectOne(id) }


}
