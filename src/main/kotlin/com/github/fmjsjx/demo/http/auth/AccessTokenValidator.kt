package com.github.fmjsjx.demo.http.auth

import com.github.fmjsjx.demo.http.server.RouteErrorHandler.Companion.simpleRespond
import com.github.fmjsjx.libnetty.http.server.HttpRequestContext
import com.github.fmjsjx.libnetty.http.server.HttpResult
import com.github.fmjsjx.libnetty.http.server.middleware.Middleware
import com.github.fmjsjx.libnetty.http.server.middleware.MiddlewareChain
import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.util.AsciiString
import org.springframework.stereotype.Component
import java.util.concurrent.CompletionStage
import kotlin.jvm.optionals.getOrNull

@Component
class AccessTokenValidator(
    private val service: AccessTokenService,
) : Middleware {

    companion object {
        private val X_TOKEN: AsciiString = AsciiString.cached("x-token")
    }

    override fun apply(ctx: HttpRequestContext, next: MiddlewareChain): CompletionStage<HttpResult> {
        val accessTokenId = ctx.headers()[X_TOKEN] ?: return unauthorized(ctx)
        return service.findOneAsync(accessTokenId, ctx.eventLoop()).thenCompose { value ->
            value.getOrNull()?.let { token ->
                ctx.property(AccessToken.KEY, value.get())
                next.doNext(ctx)
            } ?: forbidden(ctx)
        }
    }

    private fun forbidden(ctx: HttpRequestContext): CompletionStage<HttpResult> =
        ctx.simpleRespond(HttpResponseStatus.FORBIDDEN, 100_002, "Token异常")

    private fun unauthorized(ctx: HttpRequestContext): CompletionStage<HttpResult> =
        ctx.simpleRespond(HttpResponseStatus.UNAUTHORIZED, 100_001, "未认证")

}
