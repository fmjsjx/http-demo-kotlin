package com.github.fmjsjx.demo.http.core.service

import com.github.fmjsjx.demo.http.api.ProcedureException
import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.common.redis.RedisWrapped
import com.github.fmjsjx.demo.http.common.util.toJsonString
import com.github.fmjsjx.demo.http.core.api.Events.CROSS_DAY
import com.github.fmjsjx.demo.http.core.api.ProcedureContext
import com.github.fmjsjx.demo.http.core.entity.model.Player
import com.github.fmjsjx.demo.http.core.entity.model.Player.BNAME_UID
import com.github.fmjsjx.demo.http.core.entity.model.toBsonUpdate
import com.github.fmjsjx.demo.http.core.entity.model.toUpdateFilter
import com.github.fmjsjx.demo.http.exception.ConcurrentlyUpdateException
import com.github.fmjsjx.demo.http.util.ConfigUtil
import com.github.fmjsjx.libcommon.json.parseJSONObject

import com.github.fmjsjx.libcommon.redis.RedisDistributedLock
import com.github.fmjsjx.libcommon.util.DateTimeUtil
import com.github.fmjsjx.libcommon.util.RandomUtil
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.reactive.awaitSingle
import org.bson.BsonDocument
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.CompletionStage
import kotlin.jvm.Throws

@Service
class PlayerService(
    private val redisCache: RedisCacheService,
    private val mongodbManager: MongoDBManager,
) : RedisWrapped by redisCache {

    companion object {
        private const val CACHE_TTL = 1800L
        private const val DEFAULT_LOCK_SCOPE = "default"

        private fun Long.toRedisLockKey(scope: String): String = "lock:player:{$this}:$scope"
        private fun Long.toCacheKey(): String = "cache:player:{$this}"
        private fun Player.toCacheKey(): String = uid.toCacheKey()

        private fun generateGuestNickname() =
            "玩家${RandomUtil.randomInRange(1_000_000_000, 1_999_999_999)}"
    }

    override val logger = LoggerFactory.getLogger(javaClass)!!

    suspend fun tryLock(
        uid: Long,
        scope: String = DEFAULT_LOCK_SCOPE,
        timeout: Long = 5,
        maxWait: Long = 10_000,
        eachWait: Long = 200,
    ): RedisDistributedLock<String, String>? =
        tryDistributedLock(
            uid.toRedisLockKey(scope),
            timeout = timeout,
            maxWait = maxWait,
            eachWait = eachWait,
        ) { System.currentTimeMillis().toString(36) }

    suspend fun lock(
        uid: Long,
        scope: String = DEFAULT_LOCK_SCOPE,
        timeout: Long = 5,
        maxWait: Long = 10_000,
        eachWait: Long = 200,
        exceptionSupplier: () -> Throwable = { ProcedureException(1_001, "您的手速太快了，请稍等几秒再试哟") },
    ): RedisDistributedLock<String, String> =
        tryLock(uid, scope, timeout, maxWait, eachWait) ?: throw exceptionSupplier.invoke()

    private fun playerCollection(groupId: Int): MongoCollection<BsonDocument> =
        mongodbManager.database(groupId).getCollection(Player.COLLECTION_NAME, BsonDocument::class.java)

    private fun AccessToken.playerCollection() = playerCollection(groupId)

    suspend fun createPlayer(
        accessToken: AccessToken,
        nickname: String?,
        faceUrl: String?,
    ): Player {
        val player = Player().init(accessToken, nickname, faceUrl)
        val now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)
        player.createTime = now
        player.updateTime = now
        player.reset()
        accessToken.playerCollection().insertOne(player.toBson()).awaitSingle()
        return player
    }

    private fun Player.init(accessToken: AccessToken, nickname: String?, faceUrl: String?): Player = apply {
        uid = accessToken.uid
        // may load config here
        // reference
        preferences.apply {
            custom = ""
            features = java.util.List.copyOf(accessToken.features)
        }
        // basic
        basic.apply {
            this.nickname = nickname ?: ""
            this.faceUrl = faceUrl ?: ""
        }
        // login
        login.apply {
            count = 1
            days = 1
            continuousDays = 1
            maxContinuousDays = 1
            loginTime = accessToken.loginTime
            ip = accessToken.remoteAddress
        }
        // guide
        guide.apply {
            status = 0
        }
        // wallet
        wallet.apply {
            coinTotal = 0
            diamond = 0
        }
        // items
        // daily
        daily.apply {
            day = accessToken.loginTime.toLocalDate()
        }
    }

    fun cacheAsync(player: Player): CompletionStage<String> =
        redisCache.setDataAsync(player.toCacheKey(), player.toFastjson2Node(), CACHE_TTL) { it.toJsonString() }

    suspend fun createGuestPlayer(accessToken: AccessToken): Player =
        try {
            createPlayer(accessToken, generateGuestNickname(), null)
        } catch (_: DuplicateKeyException) {
            getGuestPlayer(accessToken)
        }


    suspend fun getGuestPlayer(accessToken: AccessToken): Player =
        findOne(accessToken) ?: createGuestPlayer(accessToken)

    suspend fun findOne(accessToken: AccessToken): Player? = accessToken.run {
        redisCache.retrieveData(
            uid.toCacheKey(),
            CACHE_TTL,
            { it.toFastjson2Node().toJsonString() },
            { Player().loadFastjson2Node(it.parseJSONObject()) },
            true,
        ) {
            playerCollection().find(eq(BNAME_UID, uid)).first().awaitSingle()?.let { Player().load(it) }
        }
    }

    suspend fun getFixedPlayerWithContext(
        accessToken: AccessToken,
        exceptionSupplier: () -> Throwable = { ProcedureException(100, "数据访问异常") },
    ): Pair<ProcedureContext, Player> = getPlayerWithContext(accessToken, exceptionSupplier).also { (ctx, _) ->
        fixPlayerBeforeProcessing(ctx)
    }

    suspend fun getPlayerWithContext(
        accessToken: AccessToken,
        exceptionSupplier: () -> Throwable = { ProcedureException(100, "数据访问异常") },
    ): Pair<ProcedureContext, Player> =
        findOne(accessToken)?.let { ProcedureContext.create(accessToken, it) to it } ?: throw exceptionSupplier.invoke()

    fun fixPlayerBeforeProcessing(ctx: ProcedureContext): Boolean {
        var changed = false
        var player = ctx.player!!
        // may fix data for old versions
        // ...
        // check cross day
        val date = ctx.time.toLocalDate()
        player.daily.takeUnless { date.isEqual(it.day) }?.apply {
            // cross day
            changed = true
            ctx.event(CROSS_DAY)
            // fix login
            player.login.apply {
                increaseDays()
                if (date.minusDays(1).isEqual(day)) {
                    // cross day
                    increaseContinuousDays().takeIf { it > maxContinuousDays }?.let(::setMaxContinuousDays)
                    if (gamingCount == 0) {
                        gamingDays = 0
                    }
                } else {
                    continuousDays = 1
                    gamingDays = 0
                }
            }
            // fix daily values
            day = date
            // reset other data
            coin = 0
            diamond = 0
            videoCount = 0
            videoCounts.clear()
            gamingCount = 0
        }
        return changed
    }

    @Throws(ConcurrentlyUpdateException::class)
    suspend fun update(ctx: ProcedureContext) {
        if (updateCas(ctx)) {
            cacheAsync(ctx.player!!)
        } else {
            redisCache.delAsync(ctx.player!!.toCacheKey())
            throw ConcurrentlyUpdateException
        }
    }

    suspend fun updateCas(ctx: ProcedureContext): Boolean {
        // may process before user
        return updateCas(ctx.accessToken, ctx.player!!)
    }

    private suspend fun updateCas(accessToken: AccessToken, player: Player): Boolean {
        if (player.anyUpdated()) {
            player.updateTime = DateTimeUtil.ofEpochMilli(System.currentTimeMillis())
            val filter = player.toUpdateFilter()
            player.increaseUpdateVersion()
            val update = player.toBsonUpdate()
            logger.debug("[mongodb:player] update one {} {} >>>", filter, update)
            return accessToken.playerCollection().runCatching {
                updateOne(filter, update).awaitSingle()
            }.onSuccess {
                logger.debug("[mongodb:player] update one result {} {} <<< {}", filter, update, it)
            }.onFailure {
                logger.error("[mongodb:player] update one failed {} {}", filter, update, it)
            }.getOrThrow().modifiedCount > 0
        }
        return true
    }

    final suspend inline fun <R> autoRetry(
        exceptionSupplier: () -> Throwable = { ProcedureException(100, "数据访问异常") },
        action: suspend (Int) -> R,
    ): R {
        (0..ConfigUtil.retryCount).forEach { retryCount ->
            try {
                return action(retryCount)
            } catch (_: ConcurrentlyUpdateException) {
                // auto retry when meet ConcurrentlyUpdateException
            }
        }
        throw exceptionSupplier.invoke()
    }

    suspend fun fixPlayerAndUpdate(ctx: ProcedureContext): Boolean {
        if (fixPlayerBeforeProcessing(ctx)) {
            update(ctx)
            return true
        }
        return false
    }

}
