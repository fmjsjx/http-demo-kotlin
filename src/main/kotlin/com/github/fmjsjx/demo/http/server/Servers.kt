package com.github.fmjsjx.demo.http.server

import com.github.fmjsjx.demo.http.auth.AccessTokenValidator
import com.github.fmjsjx.demo.http.server.ServerProperties.KeyCertProperties
import com.github.fmjsjx.demo.http.server.ServerProperties.SslProperties
import com.github.fmjsjx.libnetty.handler.ssl.ChannelSslInitializer
import com.github.fmjsjx.libnetty.handler.ssl.SniHandlerProviders
import com.github.fmjsjx.libnetty.handler.ssl.SslContextProviders
import com.github.fmjsjx.libnetty.http.HttpContentCompressorProvider
import com.github.fmjsjx.libnetty.http.server.DefaultHttpServer
import com.github.fmjsjx.libnetty.http.server.component.JsonLibrary
import com.github.fmjsjx.libnetty.http.server.component.MixedJsonLibrary
import com.github.fmjsjx.libnetty.http.server.middleware.AccessLogger
import com.github.fmjsjx.libnetty.http.server.middleware.AccessLogger.Slf4jLoggerWrapper
import com.github.fmjsjx.libnetty.http.server.middleware.PathFilterMiddleware
import com.github.fmjsjx.libnetty.http.server.middleware.Router
import com.github.fmjsjx.libnetty.transport.TransportLibrary
import io.netty.channel.Channel
import io.netty.channel.EventLoopGroup
import io.netty.handler.codec.http.HttpMethod.*
import io.netty.handler.codec.http.cors.CorsConfigBuilder
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder.forServer
import io.netty.util.concurrent.DefaultThreadFactory
import io.netty.util.internal.SystemPropertyUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.math.max

@Component
class Servers(
    private val serverProperties: ServerProperties,
    private val routeErrorHandler: RouteErrorHandler,
    private val router: Router,
    private val accessTokenValidator: AccessTokenValidator,
) : InitializingBean, DisposableBean, CommandLineRunner {

    companion object {
        private val logger = LoggerFactory.getLogger(Servers::class.java)!!
        private val corsConfig =
            CorsConfigBuilder.forAnyOrigin().allowedRequestMethods(GET, POST, PUT, PATCH, DELETE, HEAD)
                .allowedRequestHeaders("*").allowNullOrigin().build()
        private val transportLibrary = TransportLibrary.getDefault()
    }

    lateinit var httpServer: DefaultHttpServer
    lateinit var httpBossGroup: EventLoopGroup
    lateinit var workerGroup: EventLoopGroup

    private val jsonLibrary: JsonLibrary = MixedJsonLibrary.recommended(JsonLibrary.EmptyWay.EMPTY)

    override fun afterPropertiesSet() {
        val http = serverProperties.http
        // public http server
        val server = DefaultHttpServer("http", SystemPropertyUtil.getInt("server.http.port", http.port))
        http.address?.let(server::address)
        if (http.sslEnabled()) {
            server.enableSsl(http.ssl)
        }
        server.corsConfig(corsConfig)
            .soBackLog(1024)
            .component(jsonLibrary)
            .component(routeErrorHandler)
            .applyCompressionOptions(HttpContentCompressorProvider.defaultOptions())
        server.defaultHandlerProvider()
            .addLast(AccessLogger(Slf4jLoggerWrapper("accessLogger"), http.accessLogPattern))
            .addLast(PathFilterMiddleware.toFilter("/api/auth").negate(), accessTokenValidator)
            .addLast(router)
            .lastChain(NotFoundMiddlewareChain)
        this.httpServer = server
    }

    private fun DefaultHttpServer.enableSsl(ssl: SslProperties) {
        if (ssl.sniEnabled()) {
            val defaultSslContext = forServer(ssl.keyCertChainFile(), ssl.keyFile(), ssl.keyPassword).build()
            val mapping = ssl.sni.mapping.mapValues { (_, cfg) ->
                forServer(cfg.keyCertChainFile(), cfg.keyFile(), cfg.keyPassword).build()
            }
            ChannelSslInitializer.of(SniHandlerProviders.create(defaultSslContext, mapping))
        } else {
            ChannelSslInitializer.of<Channel>(SslContextProviders.simple(ssl.toSslContext()))
        }.let(::enableSsl)
    }

    private fun KeyCertProperties.toSslContext(): SslContext =
        forServer(keyCertChainFile(), keyFile(), keyPassword).build()

    override fun destroy() {
        if (::httpServer.isInitialized) {
            httpServer.takeIf { it.isRunning }?.let {
                it.shutdown()
                logger.info("HTTP Server {} stopped.", it)
            }
        }
        if (::httpBossGroup.isInitialized) {
            httpBossGroup.takeUnless(EventLoopGroup::isShuttingDown)?.run(EventLoopGroup::shutdownGracefully)
        }
    }

    override fun run(vararg args: String) {
        serverProperties.takeUnless { ::workerGroup.isInitialized }?.run {
            workerGroup = transportLibrary.createGroup(max(0, ioThreads), DefaultThreadFactory("io-worker"))
        }
        httpServer.takeUnless { it.isRunning }?.let { server ->
            httpBossGroup = transportLibrary.createGroup(1, DefaultThreadFactory("http-boss"))
            server.transport(httpBossGroup, workerGroup, transportLibrary.serverChannelClass()).startup()
            logger.info("HTTP Server {} started.", server)
        }
    }

}
