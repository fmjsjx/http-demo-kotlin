package com.github.fmjsjx.demo.http.core.service

import com.alibaba.fastjson2.TypeReference as Fastjson2TypeReference
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.github.fmjsjx.demo.http.common.redis.AbstractRedisWrapped
import com.github.fmjsjx.libcommon.json.JsonDecoder
import com.github.fmjsjx.libcommon.json.JsonEncoder
import com.github.fmjsjx.libcommon.json.fastjson2Library
import com.github.fmjsjx.libcommon.json.jsoniterLibrary
import com.github.fmjsjx.libcommon.json.parseFastjson2
import com.github.fmjsjx.libcommon.json.parseJackson2
import com.github.fmjsjx.libcommon.json.parseJsoniter
import com.jsoniter.spi.TypeLiteral
import io.lettuce.core.api.StatefulRedisConnection
import kotlinx.coroutines.future.await
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.lang.reflect.Type
import java.util.Optional
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor
import kotlin.jvm.optionals.getOrNull

@Service
class RedisCacheService(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Qualifier("globalRedisConnection")
    override val globalRedisConnection: StatefulRedisConnection<String, String>,
) : AbstractRedisWrapped() {

    fun <T : Any> setDataAsync(
        key: String,
        data: T,
        ttl: Long,
        encode: (T) -> String,
    ): CompletionStage<String> = setexAsync(key, ttl, encode(data))

    fun <T : Any> setJsonAsync(
        key: String,
        data: T,
        ttl: Long,
        jsonEncoder: JsonEncoder = fastjson2Library,
    ): CompletionStage<String> = setDataAsync(key, data, ttl, jsonEncoder::dumpsToString)

    fun <V : Number> setNumberAsync(
        key: String,
        value: V,
        ttl: Long,
    ): CompletionStage<String> = setexAsync(key, ttl, value.toString())

    fun setEmptyAsync(key: String, ttl: Long): CompletionStage<String> = setexAsync(key, ttl, "")

    fun <T : Any> getJsonObjectAsync(
        key: String,
        type: Class<T>,
        executor: Executor,
        ttl: Long = 0,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
    ): CompletionStage<Optional<T>?> = getDataAsync(key, executor, ttl) { value -> jsonDecoder.loads(value, type) }

    final inline fun <reified T : Any> getJsonObjectAsync(
        key: String,
        executor: Executor,
        ttl: Long = 0,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
    ): CompletionStage<Optional<T>?> = getJsonObjectAsync(key, T::class.java, executor, ttl, jsonDecoder)

    fun <T : Any> getJsonAsync(
        key: String,
        typeLiteral: TypeLiteral<T>,
        executor: Executor,
        ttl: Long = 0,
    ): CompletionStage<Optional<T>?> = getDataAsync(key, executor, ttl) { value -> value.parseJsoniter(typeLiteral) }

    fun <T : Any> getJsonAsync(
        key: String,
        typeReference: Fastjson2TypeReference<T>,
        executor: Executor,
        ttl: Long = 0,
    ): CompletionStage<Optional<T>?> = getDataAsync(key, executor, ttl) { value -> value.parseFastjson2(typeReference) }

    fun <T : Any> getJsonAsync(
        key: String,
        typeReference: TypeReference<T>,
        executor: Executor,
        ttl: Long = 0,
    ): CompletionStage<Optional<T>?> = getDataAsync(key, executor, ttl) { value -> value.parseJackson2(typeReference) }

    fun <T : Any> getJsonAsync(
        key: String,
        javaType: JavaType,
        executor: Executor,
        ttl: Long = 0,
    ): CompletionStage<Optional<T>?> = getDataAsync(key, executor, ttl) { value -> value.parseJackson2(javaType) }

    fun <T : Any> getJsonAsync(
        key: String,
        type: Type,
        executor: Executor,
        ttl: Long = 0,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
    ): CompletionStage<Optional<T>?> = getDataAsync(key, executor, ttl) { value -> jsonDecoder.loads(value, type) }

    fun <T : Any> getDataAsync(
        key: String,
        executor: Executor,
        ttl: Long = 0,
        decode: (String) -> T,
    ): CompletionStage<Optional<T>?> = if (ttl > 0) {
        getexAsync(key, ttl)
    } else {
        getAsync(key)
    }.thenApplyAsync({ value ->
        if (value == null) {
            null
        } else {
            if (value.isEmpty()) {
                Optional.empty()
            } else {
                Optional.of(decode(value))
            }
        }
    }, executor)

    fun getIntAsync(key: String, executor: Executor, ttl: Long = 0): CompletionStage<Optional<Int>?> =
        getDataAsync(key, executor, ttl, String::toInt)

    fun getLongAsync(key: String, executor: Executor, ttl: Long = 0): CompletionStage<Optional<Long>?> =
        getDataAsync(key, executor, ttl, String::toLong)

    fun getDoubleAsync(key: String, executor: Executor, ttl: Long = 0): CompletionStage<Optional<Double>?> =
        getDataAsync(key, executor, ttl, String::toDouble)


    fun <T : Any, R : CompletionStage<T?>> retrieveDataAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        encode: (T) -> String,
        decode: (String) -> T,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> {
        return if (renewTtl) {
            getDataAsync(key, executor, ttl, decode)
        } else {
            getDataAsync(key, executor, decode = decode)
        }.thenCompose { value ->
            if (value != null) {
                CompletableFuture.completedStage(value.getOrNull())
            } else {
                getDataAsync().whenCompleteAsync({ data, _ ->
                    if (data != null) {
                        setDataAsync(key, data, ttl, encode)
                    } else {
                        setEmptyAsync(key, ttl)
                    }
                }, executor)
            }
        }
    }

    fun <T : Any, R : CompletionStage<T?>> retrieveJsonObjectAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        type: Class<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveDataAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> jsonDecoder.loads(value, type) },
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    final inline fun <reified T : Any, R : CompletionStage<T?>> retrieveJsonObjectAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        jsonEncoder: JsonEncoder = fastjson2Library,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
        renewTtl: Boolean = false,
        noinline getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveJsonObjectAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        type = T::class.java,
        jsonEncoder = jsonEncoder,
        jsonDecoder = jsonDecoder,
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    fun <T : Any, R : CompletionStage<T?>> retrieveJsonAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        type: Type,
        jsonEncoder: JsonEncoder = fastjson2Library,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveDataAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> jsonDecoder.loads(value, type) },
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    fun <T : Any, R : CompletionStage<T?>> retrieveJsonAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        typeLiteral: TypeLiteral<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveDataAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseJsoniter(typeLiteral) },
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    fun <T : Any, R : CompletionStage<T?>> retrieveJsonAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        typeReference: Fastjson2TypeReference<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveDataAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseFastjson2(typeReference) },
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    fun <T : Any, R : CompletionStage<T?>> retrieveJsonAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        typeReference: TypeReference<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveDataAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseJackson2(typeReference) },
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    fun <T : Any, R : CompletionStage<T?>> retrieveJsonAsync(
        key: String,
        executor: Executor,
        ttl: Long,
        javaType: JavaType,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getDataAsync: () -> R,
    ): CompletionStage<T?> = retrieveDataAsync(
        key = key,
        executor = executor,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseJackson2(javaType) },
        renewTtl = renewTtl,
        getDataAsync = getDataAsync,
    )

    suspend fun <T : Any> getJsonObject(
        key: String,
        type: Class<T>,
        ttl: Long = 0,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
    ): Optional<T>? = getData(key, ttl) { value -> jsonDecoder.loads(value, type) }

    final suspend inline fun <reified T : Any> getJsonObject(
        key: String,
        ttl: Long = 0,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
    ): Optional<T>? = getJsonObject(key, T::class.java, ttl, jsonDecoder)

    suspend fun <T : Any> getJson(
        key: String,
        typeLiteral: TypeLiteral<T>,
        ttl: Long = 0,
    ): Optional<T>? = getData(key, ttl) { value -> value.parseJsoniter(typeLiteral) }

    suspend fun <T : Any> getJson(
        key: String,
        typeReference: Fastjson2TypeReference<T>,
        ttl: Long = 0,
    ): Optional<T>? = getData(key, ttl) { value -> value.parseFastjson2(typeReference) }

    suspend fun <T : Any> getJson(
        key: String,
        typeReference: TypeReference<T>,
        ttl: Long = 0,
    ): Optional<T>? = getData(key, ttl) { value -> value.parseJackson2(typeReference) }

    suspend fun <T : Any> getJson(
        key: String,
        javaType: JavaType,
        ttl: Long = 0,
    ): Optional<T>? = getData(key, ttl) { value -> value.parseJackson2(javaType) }

    suspend fun <T : Any> getJson(
        key: String,
        type: Type,
        ttl: Long = 0,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
    ): Optional<T>? = getData(key, ttl) { value -> jsonDecoder.loads(value, type) }

    suspend fun <T : Any> getData(
        key: String,
        ttl: Long = 0,
        decode: (String) -> T,
    ): Optional<T>? = if (ttl > 0) {
        getexAsync(key, ttl)
    } else {
        getAsync(key)
    }.await()?.let { value ->
        if (value.isEmpty()) {
            Optional.empty()
        } else {
            runCatching {
                Optional.of(decode(value))
            }.onFailure {
                logger.warn("[redis:cache] Decode data failed - {} <<< {}", key, value, it)
                delAsync(key)
            }.getOrNull()
        }
    }

    suspend fun getInt(key: String, ttl: Long = 0): Optional<Int>? = getData(key, ttl, String::toInt)

    suspend fun getLong(key: String, ttl: Long = 0): Optional<Long>? = getData(key, ttl, String::toLong)

    suspend fun getDouble(key: String, ttl: Long = 0): Optional<Double>? = getData(key, ttl, String::toDouble)

    suspend fun <T : Any> retrieveData(
        key: String,
        ttl: Long,
        encode: (T) -> String,
        decode: (String) -> T,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? {
        val value = if (renewTtl) {
            getData(key, ttl, decode)
        } else {
            getData(key, decode = decode)
        }
        if (value != null) {
            return value.getOrNull()
        }
        return getData().also { data ->
            if (data != null) {
                setDataAsync(key, data, ttl, encode)
            } else {
                setEmptyAsync(key, ttl)
            }
        }
    }

    suspend fun <T : Any> retrieveJsonObject(
        key: String,
        ttl: Long,
        type: Class<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? = retrieveData(
        key = key,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> jsonDecoder.loads(value, type) },
        renewTtl = renewTtl,
        getData = getData,
    )

    final suspend inline fun <reified T : Any> retrieveJsonObject(
        key: String,
        ttl: Long,
        jsonEncoder: JsonEncoder = fastjson2Library,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
        renewTtl: Boolean = false,
        noinline getData: suspend () -> T?,
    ): T? = retrieveJsonObject(
        key = key,
        ttl = ttl,
        type = T::class.java,
        jsonEncoder = jsonEncoder,
        jsonDecoder = jsonDecoder,
        renewTtl = renewTtl,
        getData = getData,
    )

    suspend fun <T : Any> retrieveJson(
        key: String,
        ttl: Long,
        type: Type,
        jsonEncoder: JsonEncoder = fastjson2Library,
        jsonDecoder: JsonDecoder<*> = jsoniterLibrary,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? = retrieveData(
        key = key,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> jsonDecoder.loads(value, type) },
        renewTtl = renewTtl,
        getData = getData,
    )

    suspend fun <T : Any> retrieveJson(
        key: String,
        ttl: Long,
        typeLiteral: TypeLiteral<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? = retrieveData(
        key = key,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseJsoniter(typeLiteral) },
        renewTtl = renewTtl,
        getData = getData,
    )

    suspend fun <T : Any> retrieveJson(
        key: String,
        ttl: Long,
        typeReference: Fastjson2TypeReference<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? = retrieveData(
        key = key,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseFastjson2(typeReference) },
        renewTtl = renewTtl,
        getData = getData,
    )

    suspend fun <T : Any> retrieveJson(
        key: String,
        ttl: Long,
        typeReference: TypeReference<T>,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? = retrieveData(
        key = key,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseJackson2(typeReference) },
        renewTtl = renewTtl,
        getData = getData,
    )

    suspend fun <T : Any> retrieveJson(
        key: String,
        ttl: Long,
        javaType: JavaType,
        jsonEncoder: JsonEncoder = fastjson2Library,
        renewTtl: Boolean = false,
        getData: suspend () -> T?,
    ): T? = retrieveData(
        key = key,
        ttl = ttl,
        encode = jsonEncoder::dumpsToString,
        decode = { value -> value.parseJackson2(javaType) },
        renewTtl = renewTtl,
        getData = getData,
    )

}
