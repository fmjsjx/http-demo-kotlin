package com.github.fmjsjx.demo.http.server

import com.github.fmjsjx.libnetty.http.server.HttpRequestContext
import com.github.fmjsjx.libnetty.http.server.HttpResult
import com.github.fmjsjx.libnetty.http.server.middleware.MiddlewareChain
import com.github.fmjsjx.demo.http.server.RouteErrorHandler.Companion.simpleRespond
import io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND
import java.util.concurrent.CompletionStage

object NotFoundMiddlewareChain : MiddlewareChain {

    override fun doNext(ctx: HttpRequestContext): CompletionStage<HttpResult> =
        ctx.simpleRespond(NOT_FOUND, 404_000, "Not found path `${ctx.path()}`")

}