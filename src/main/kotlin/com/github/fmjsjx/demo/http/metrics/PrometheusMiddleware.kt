package com.github.fmjsjx.demo.http.metrics

import com.github.fmjsjx.libnetty.http.server.HttpRequestContext
import com.github.fmjsjx.libnetty.http.server.HttpResult
import com.github.fmjsjx.libnetty.http.server.middleware.Middleware
import com.github.fmjsjx.libnetty.http.server.middleware.MiddlewareChain
import com.github.fmjsjx.libnetty.http.server.middleware.Router.MatchedRoute
import io.prometheus.metrics.core.metrics.Counter
import io.prometheus.metrics.core.metrics.Histogram
import io.prometheus.metrics.model.registry.PrometheusRegistry
import io.prometheus.metrics.model.snapshots.Labels
import io.prometheus.metrics.model.snapshots.Unit
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.CompletionStage


@Component
class PrometheusMiddleware(
    prometheusRegistry: PrometheusRegistry,
    @Value($$"${spring.application.name}")
    applicationName: String
) : Middleware {

    val requestsDuration: Histogram = Histogram.builder().name("requests_duration_seconds")
        .help("Request duration in seconds.")
        .constLabels(Labels.of("application", applicationName, "caller", "", "kind", "http"))
        .labelNames("operation")
        .classicUpperBounds(0.05, 0.1, 0.25, 0.5, 1.0, 1.5, 2.0, 5.0)
        .unit(Unit.SECONDS)
        .register(prometheusRegistry)
    val requestsTotal: Counter = Counter.builder().name("requests_total")
        .help("Total requests.")
        .constLabels(Labels.of("application", applicationName, "caller", "", "kind", "http"))
        .labelNames("operation", "code", "reason")
        .register(prometheusRegistry)

    override fun apply(ctx: HttpRequestContext, next: MiddlewareChain): CompletionStage<HttpResult> =
        next.doNext(ctx).whenComplete { result, _ ->
            val status = result.responseStatus()
            val code = status.codeAsText().toString()
            val reason = when (status.code()) {
                200 -> ""
                else -> status.reasonPhrase()
            }
            val requestContext = result.requestContext()
            val method = requestContext.method().toString()
            val path = requestContext.matchedRoute().map(MatchedRoute::path).orElseGet(requestContext::path)
            val operation = "$method $path"
            requestsTotal.labelValues(operation, code, reason).inc()
            requestsDuration.labelValues(operation).observe(result.nanoUsed() / 1_000_000_000.0)
        }

}