package com.github.fmjsjx.demo.http.common.redis

import com.github.fmjsjx.libcommon.redis.LuaScript
import com.github.fmjsjx.libcommon.redis.RedisDistributedLock
import com.github.fmjsjx.libcommon.redis.RedisUtil
import com.github.fmjsjx.libcommon.redis.tryDistributedLock
import io.lettuce.core.KeyValue
import io.lettuce.core.XAddArgs
import io.lettuce.core.ZAddArgs
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import org.slf4j.Logger
import java.util.UUID
import java.util.concurrent.CompletionStage

interface RedisWrapped {

    companion object {

        val GET_EX: LuaScript<String?> = LuaScript.forValue(
            """
                local value = redis.call('get', KEYS[1])
                if value ~= nil then
                  redis.call('expire', KEYS[1], ARGV[1])
                end
                return value
            """.trimIndent()
        )

        const val KEY_IDENTITY_MAP = "identity:map"
    }

    val logger: Logger
    val defaultName: String get() = "global"
    val globalRedisConnection: StatefulRedisConnection<String, String>
    val globalRedisAsync: RedisAsyncCommands<String, String> get() = globalRedisConnection.async()

    fun getAsync(
        key: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<String?> {
        logger.debug("[redis:{}:async] GET {} >>>", name, key)
        return redisAsync.get(key).whenComplete { value, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] GET {} <<< {}", name, key, value)
            } else {
                logger.error("[redis:{}:async] GET {} failed", name, key, err)
            }
        }
    }

    fun setexAsync(
        key: String,
        ttl: Long,
        value: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<String> {
        logger.debug("[redis:{}:async] SETEX {} {} {} >>>", name, key, ttl, value)
        return redisAsync.setex(key, ttl, value).whenComplete { ok, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] SETEX {} {} {} <<< {}", name, key, ttl, value, ok)
            } else {
                logger.error("[redis:{}:async] SETEX {} {} {} failed", name, key, ttl, value, err)
            }
        }
    }

    fun hsetAsync(
        key: String,
        field: String,
        value: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync
    ): CompletionStage<Boolean> {
        logger.debug("[redis:{}:async] HSET {} {} {} >>>", name, key, field, value)
        return redisAsync.hset(key, field, value).whenComplete { ok, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HSET {} {} {} <<< {}", name, key, field, value, ok)
            } else {
                logger.error("[redis:{}:async] HSET {} {} {} failed", name, key, field, value, err)
            }
        }
    }

    fun hmsetAsync(
        key: String,
        map: Map<String, String>,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync
    ): CompletionStage<String> {
        logger.debug("[redis:{}:async] HMSET {} {} >>>", name, key, map)
        return redisAsync.hmset(key, map).whenComplete { ok, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HMSET {} {} <<< {}", name, key, map, ok)
            } else {
                logger.error("[redis:{}:async] HMSET {} {} failed", name, key, map, err)
            }
        }
    }

    fun hgetAsync(
        key: String,
        field: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<String?> {
        logger.debug("[redis:{}:async] HGET {} {} >>>", name, key, field)
        return redisAsync.hget(key, field).whenComplete { value, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HGET {} {} <<< {}", name, key, field, value)
            } else {
                logger.error("[redis:{}:async] HGET {} {} failed", name, key, field, err)
            }
        }
    }

    fun hdelAsync(
        key: String,
        field: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> {
        logger.debug("[redis:{}:async] HDEL {} {} >>>", name, key, field)
        return redisAsync.hdel(key, field).whenComplete { num, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HDEL {} {} <<< {}", name, key, field, num)
            } else {
                logger.error("[redis:{}:async] HDEL {} {} failed", name, key, field, err)
            }
        }
    }

    fun hmgetAsync(
        key: String,
        fields: Collection<String>,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ) = hmgetAsync(
        key = key,
        fields = fields.toTypedArray(),
        name = name,
        redisAsync = redisAsync,
    )

    fun hmgetAsync(
        key: String,
        vararg fields: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<List<KeyValue<String, String>>> {
        logger.debug("[redis:{}:async] HMGET {} {} >>>", name, key, fields)
        return redisAsync.hmget(key, *fields).whenComplete { results, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HMGET {} {} <<< {}", name, key, fields, results)
            } else {
                logger.error("[redis:{}:async] HMGET {} {} failed", name, key, fields, err)
            }
        }
    }

    fun hgetallAsync(
        key: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Map<String, String>> {
        logger.debug("[redis:{}:async] HGETALL {} >>>", name, key)
        return redisAsync.hgetall(key).whenComplete { results, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HGETALL {} <<< {}", name, key, results)
            } else {
                logger.error("[redis:{}:async] HGETALL {} failed", name, key, err)
            }
        }
    }

    fun mgetAsync(
        vararg keys: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<List<KeyValue<String, String>>> {
        logger.debug("[redis:{}:async] MGET {} >>>", name, keys)
        return redisAsync.mget(*keys).whenComplete { results, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] MGET {} <<< {}", name, keys, results)
            } else {
                logger.error("[redis:{}:async] MGET {} failed", name, keys, err)
            }
        }
    }

    fun hincrbyAsync(
        key: String,
        field: String,
        amount: Long,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> {
        logger.debug("[redis:{}:async] HINCRBY {} {} {} >>>", name, key, field, amount)
        return redisAsync.hincrby(key, field, amount).whenComplete { value, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] HINCRBY {} {} {} <<< {}", name, key, field, amount, value)
            } else {
                logger.error("[redis:{}:async] HINCRBY {} {} {} failed", name, key, field, amount, err)
            }
        }
    }

    fun expireAsync(
        key: String,
        seconds: Long,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Boolean> {
        logger.debug("[redis:{}:async] EXPIRE {} {} >>>", name, key, seconds)
        return redisAsync.expire(key, seconds).whenComplete { success, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] EXPIRE {} {} <<< {}", name, key, seconds, success)
            } else {
                logger.error("[redis:{}:async] EXPIRE {} {} failed", name, key, seconds, err)
            }
        }
    }

    fun delAsync(
        key: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> {
        logger.debug("[redis:{}:async] DEL {} >>>", name, key)
        return redisAsync.del(key).whenComplete { num, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] DEL {} <<< {}", name, key, num)
            } else {
                logger.error("[redis:{}:async] DEL {} failed", name, key, err)
            }
        }
    }

    fun zaddAsync(
        key: String,
        args: ZAddArgs,
        score: Double,
        member: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> {
        logger.debug("[redis:{}:async] ZADD {} {} {} {} >>>", name, key, args, score, member)
        return redisAsync.zadd(key, args, score, member).whenComplete { num, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] ZADD {} {} {} {} <<< {}", name, key, args, score, member, num)
            } else {
                logger.error("[redis:{}:async] ZADD {} {} {} {} failed", name, key, args, score, member, err)
            }
        }
    }

    fun zrangeAsync(
        key: String,
        start: Long = 0,
        stop: Long = -1,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<List<String>> {
        logger.debug("[redis:{}:async] ZRANGE {} {} {} >>>", name, key, start, stop)
        return redisAsync.zrange(key, start, stop).whenComplete { results, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] ZRANGE {} {} {} <<< {}", name, key, start, stop, results)
            } else {
                logger.error("[redis:{}:async] ZRANGE {} {} {} failed", name, key, start, stop, err)
            }
        }
    }

    fun zrevrangeAsync(
        key: String,
        start: Long = 0,
        stop: Long = -1,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<List<String>> {
        logger.debug("[redis:{}:async] ZREVRANGE {} {} {} >>>", name, key, start, stop)
        return redisAsync.zrevrange(key, start, stop).whenComplete { results, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] ZREVRANGE {} {} {} <<< {}", name, key, start, stop, results)
            } else {
                logger.error("[redis:{}:async] ZREVRANGE {} {} {} failed", name, key, start, stop, err)
            }
        }
    }

    fun xaddAsync(
        key: String,
        fields: Map<String, String>,
        maxlen: Long = 1000,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<String> {
        logger.debug("[redis:{}:async] XADD {} MAXLEN ~ {} * {} >>>", name, key, maxlen, fields)
        var args = XAddArgs().maxlen(maxlen).approximateTrimming()
        return redisAsync.xadd(key, args, fields).whenComplete { result, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] XADD {} MAXLEN ~ {} * {} <<< {}", name, key, maxlen, fields, result)
            } else {
                logger.error("[redis:{}:async] XADD {} MAXLEN ~ {} * {} failed", name, key, maxlen, fields, err)
            }
        }
    }

    fun xackAsync(
        key: String,
        group: String,
        ids: Collection<String>,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> = xackAsync(
        key = key,
        group = group,
        ids = ids.toTypedArray(),
        name = name,
        redisAsync = redisAsync,
    )

    fun xackAsync(
        key: String,
        group: String,
        vararg ids: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> {
        logger.debug("[redis:{}:async] XACK {} {} {} >>>", name, key, group, ids)
        return redisAsync.xack(key, group, *ids).whenComplete { num, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] XACK {} {} {} <<< {}", name, key, group, ids, num)
            } else {
                logger.error("[redis:{}:async] XACK {} {} {} failed", name, key, group, ids, err)
            }
        }
    }

    fun <R> evalAsync(
        script: LuaScript<R>,
        key: String,
        vararg values: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<R> = evalAsync(
        script = script,
        keys = arrayOf(key),
        values = values,
        name = name,
        redisAsync = redisAsync,
    )

    fun <R> evalAsync(
        script: LuaScript<R>,
        keys: Array<String>,
        vararg values: String,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<R> {
        logger.debug("[redis:{}:async] EVAL {} {} {} >>>", name, script, keys, values)
        return RedisUtil.eval(redisAsync, script, keys, *values).whenComplete { result, err ->
            if (err == null) {
                logger.debug("[redis:{}:async] EVAL {} {} {} <<< {}", name, script, keys, values, result)
            } else {
                logger.error("[redis:{}:async] EVAL {} {} {} failed", name, script, keys, values, err)
            }
        }
    }

    suspend fun tryDistributedLock(
        key: String,
        timeout: Long = 5,
        maxWait: Long = 10_000,
        eachWait: Long = 200,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
        valueSupplier: () -> String = { UUID.randomUUID().toString() },
    ): RedisDistributedLock<String, String>? {
        logger.debug("[redis:{}:suspend] Try distributed lock {} {} {} {} >>>", name, key, timeout, maxWait, eachWait)
        return redisAsync.runCatching {
            tryDistributedLock(key, timeout, maxWait, eachWait, valueSupplier)
        }.onSuccess {
            logger.debug("[redis:{}:suspend] Try distributed lock {} <<< {}", name, key, it)
        }.onFailure { err ->
            logger.error("[redis:{}:suspend] Try distributed lock {} failed", name, key, err)
        }.getOrThrow()
    }

    fun nextIdAsync(
        field: String,
        amount: Long = 1,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<Long> = hincrbyAsync(KEY_IDENTITY_MAP, field, amount, name, redisAsync)

    fun getexAsync(
        key: String,
        timeout: Long,
        name: String = defaultName,
        redisAsync: RedisAsyncCommands<String, String> = globalRedisAsync,
    ): CompletionStage<String?> = evalAsync(
        script = GET_EX,
        key = key,
        values = arrayOf(timeout.toString()),
        name = name,
        redisAsync = redisAsync,
    )

}
