package com.github.fmjsjx.demo.http.core.service

import com.github.fmjsjx.demo.http.common.util.use
import io.netty.util.concurrent.DefaultThreadFactory
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.Volatile

class InMemoryRollingCacheService(
    val name: String,
    private val rollingPeriod: Duration = Duration.ofMinutes(1),
    private val autoRenew: Boolean = false,
    private val scheduledExecutor: ScheduledExecutorService = Scheduler(),
    private val cacheInitialCapacity: Int = 256,
) {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()

    @Volatile
    private var cache0: ConcurrentHashMap<String, Optional<*>> = createCacheMap()

    @Volatile
    private var cache1: ConcurrentHashMap<String, Optional<*>> = createCacheMap()

    init {
        val periodSeconds = rollingPeriod.toSeconds()
        logger.info("[cache:{}] Initialize rolling interval({}s) task.", name, periodSeconds)
        scheduledExecutor.scheduleAtFixedRate(::rollingCaches, periodSeconds, periodSeconds, TimeUnit.SECONDS)
    }

    private fun createCacheMap(): ConcurrentHashMap<String, Optional<*>> = ConcurrentHashMap(cacheInitialCapacity)

    private fun rollingCaches() {
        writeLock.use {
            cache0 = cache1
            cache1 = createCacheMap()
        }
    }

    fun <T : Any> cacheValue(key: String, value: T?): Optional<T> = readLock.use {
        Optional.ofNullable(value).also {
            cache0[key] = it
            cache1[key] = it
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getCachedValue(key: String): Optional<T>? = readLock.use {
        cache0[key]?.also {
            if (autoRenew) {
                cache1[key] = it
            }
        }
    }?.let { it as Optional<T> }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> removeCachedValue(key: String): Optional<T>? = readLock.use {
        cache0.remove(key)?.also { cache1.remove(key) }
    }?.let { it as Optional<T> }


    override fun toString(): String {
        return "InMemoryRollingCacheService(name=$name, rollingPeriod=$rollingPeriod, scheduledExecutor=$scheduledExecutor, cacheInitialCapacity=$cacheInitialCapacity, cache0.size=${cache0.size})"
    }

    private class Scheduler() : ScheduledExecutorService by scheduler {

        companion object {
            val scheduler = ScheduledThreadPoolExecutor(1, DefaultThreadFactory("LocalCacheScheduler", true)).apply {
                removeOnCancelPolicy = true
            }
        }

    }

}
