package com.github.fmjsjx.demo.http.controller

import com.github.fmjsjx.demo.http.api.ItemBox
import com.github.fmjsjx.demo.http.api.ProcedureResult
import com.github.fmjsjx.demo.http.api.ProcedureResultData
import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.core.service.PlayerService
import com.github.fmjsjx.demo.http.util.ItemUtil
import com.github.fmjsjx.libnetty.http.server.annotation.HttpGet
import com.github.fmjsjx.libnetty.http.server.annotation.HttpPath
import com.github.fmjsjx.libnetty.http.server.annotation.HttpPost
import com.github.fmjsjx.libnetty.http.server.annotation.JsonBody
import com.github.fmjsjx.libnetty.http.server.annotation.PropertyValue
import com.github.fmjsjx.myboot.http.route.annotation.RouteController
import org.slf4j.LoggerFactory
import kotlin.math.max


@RouteController
@HttpPath("/api/v1/players/@self")
class SelfController(
    private val playerService: PlayerService,
) {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    @HttpGet
    suspend fun get(@PropertyValue accessToken: AccessToken): ProcedureResult {
        logger.debug("[api:self] GET self player: {}", accessToken)
        return playerService.lock(accessToken.uid).use { lock ->
            playerService.autoRetry {retryCount ->
                val (ctx, player) = playerService.getPlayerWithContext(accessToken)
                playerService.fixPlayerAndUpdate(ctx)
                ProcedureResultData.of(player, max(1, retryCount))
            }.also {
                logger.debug("[api:self] GET self player result: {}, {}", it, accessToken)
            }.toProcedureResult()
        }
    }

    @HttpPost("/test/bonus")
    suspend fun postTestBonus(
        @PropertyValue accessToken: AccessToken,
        @JsonBody bonusItems: List<ItemBox>,
    ): ProcedureResult {
        logger.debug("[api:self] POST test bonus: {}, {}", bonusItems, accessToken)
        return playerService.lock(accessToken.uid).use { lock ->
            playerService.autoRetry { retryCount ->
                val (ctx, player) = playerService.getFixedPlayerWithContext(accessToken)
                if (bonusItems.isNotEmpty()) {
                    ItemUtil.addItems(player, bonusItems)
                }
                playerService.update(ctx)
                ProcedureResultData.of(player, retryCount)
            }.also {
                logger.debug("[api:self] POST test bonus result: {}, {}", it, accessToken)
            }.toProcedureResult()
        }
    }

}