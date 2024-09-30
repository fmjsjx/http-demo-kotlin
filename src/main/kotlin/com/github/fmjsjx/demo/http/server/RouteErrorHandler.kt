package com.github.fmjsjx.demo.http.server

import com.github.fmjsjx.demo.http.api.ProcedureException
import com.github.fmjsjx.demo.http.api.ProcedureResult
import com.github.fmjsjx.libnetty.http.server.HttpRequestContext
import com.github.fmjsjx.libnetty.http.server.HttpResult
import com.github.fmjsjx.libnetty.http.server.component.ExceptionHandler
import com.github.fmjsjx.libnetty.http.server.component.JsonLibrary
import com.github.fmjsjx.libnetty.http.server.exception.HttpFailureException
import com.github.fmjsjx.libnetty.http.server.exception.ManualHttpFailureException
import com.mongodb.MongoException
import io.lettuce.core.RedisException
import io.netty.handler.codec.http.HttpHeaderValues
import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST
import io.netty.handler.codec.http.HttpResponseStatus.OK
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.concurrent.CompletionStage

@Component
class RouteErrorHandler : ExceptionHandler {

    companion object {

        private val logger = LoggerFactory.getLogger(RouteErrorHandler::class.java)

        internal fun HttpRequestContext.simpleRespond(
            status: HttpResponseStatus,
            code: Int,
            message: String,
        ): CompletionStage<HttpResult> =
            component(JsonLibrary::class.java).get().write(alloc(), ProcedureResult.fail(code, message))
                .let { simpleRespond(status, it, HttpHeaderValues.APPLICATION_JSON) }

    }

    override fun handle(
        ctx: HttpRequestContext,
        cause: Throwable
    ): Optional<CompletionStage<HttpResult>> = Optional.of(handleError(ctx, cause))

    internal fun handleError(ctx: HttpRequestContext, cause: Throwable): CompletionStage<HttpResult> =
        when (cause) {
            is ProcedureException -> {
                ctx.component(JsonLibrary::class.java).get().write(ctx.alloc(), cause.toResult())
                    .let { ctx.simpleRespond(cause.status, it, HttpHeaderValues.APPLICATION_JSON) }
            }

            is DataAccessException, is MongoException, is RedisException -> {
                ctx.simpleRespond(OK, 100, cause.message ?: "数据访问异常")
            }

            is JsonLibrary.JsonReadException -> { // 从服务器组件中抛出的JSON异常
                ctx.simpleRespond(BAD_REQUEST, 400_001, "JSON参数解析失败")
            }

            is IllegalArgumentException -> {
                ctx.simpleRespond(BAD_REQUEST, 400_000, cause.message?.let { "参数错误$it" } ?: "参数错误")
            }

            is ManualHttpFailureException -> ctx.simpleRespond(cause)

            is HttpFailureException -> ctx.simpleRespond(cause.status(), 1, cause.message ?: cause.toString())

            else -> {
                logger.error("Unexpected error occurs", cause)
                ctx.simpleRespond(OK, 1, cause.message ?: "未知错误")
            }
        }

}