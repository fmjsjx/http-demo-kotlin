package com.github.fmjsjx.demo.http

import com.github.fmjsjx.demo.http.core.service.InMemoryRollingCacheService
import io.netty.util.concurrent.DefaultEventExecutor
import io.netty.util.concurrent.DefaultThreadFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ScheduledExecutorService

@SpringBootApplication
@Configuration
@ConfigurationPropertiesScan
class HttpDemoKotlinApplication {

    @Bean("globalScheduledExecutor", destroyMethod = "shutdownGracefully")
    fun globalScheduledExecutor(): DefaultEventExecutor =
        DefaultEventExecutor(DefaultThreadFactory("global-scheduler", true))

    @Bean("accessTokenInMemoryRollingCacheService")
    fun accessTokenInMemoryRollingCacheService(globalScheduledExecutor: ScheduledExecutorService): InMemoryRollingCacheService =
        InMemoryRollingCacheService(
            name = "accessToken",
            autoRenew = true,
            scheduledExecutor = globalScheduledExecutor,
            cacheInitialCapacity = 8192,
        )

}

fun main(args: Array<String>) {
    runApplication<HttpDemoKotlinApplication>(*args)
}
