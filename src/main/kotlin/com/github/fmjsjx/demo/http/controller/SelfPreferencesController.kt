package com.github.fmjsjx.demo.http.controller

import com.github.fmjsjx.demo.http.api.ProcedureResult
import com.github.fmjsjx.demo.http.api.ProcedureResultData
import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.core.service.PlayerService
import com.github.fmjsjx.libnetty.http.server.annotation.HttpGet
import com.github.fmjsjx.libnetty.http.server.annotation.HttpPath
import com.github.fmjsjx.libnetty.http.server.annotation.HttpPut
import com.github.fmjsjx.libnetty.http.server.annotation.PropertyValue
import com.github.fmjsjx.libnetty.http.server.annotation.StringBody
import com.github.fmjsjx.myboot.http.route.annotation.RouteController
import org.slf4j.LoggerFactory

@RouteController
@HttpPath("/api/v1/players/@self/preferences")
class SelfPreferencesController(
    private val playerService: PlayerService,
) {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    @HttpGet("/custom")
    suspend fun getCustom(@PropertyValue accessToken: AccessToken): ProcedureResult {
        logger.debug("[api:self:preferences] GET custom: {}", accessToken)
        return playerService.lock(accessToken.uid).use { lock ->
            playerService.autoRetry { retryCount ->
                val (ctx, player) = playerService.getPlayerWithContext(accessToken)
                playerService.fixPlayerAndUpdate(ctx)
                ProcedureResultData.of(player.preferences.custom, player, retryCount)
            }.also {
                logger.debug("[api:self:preferences] GET custom result: {}, {}", it, accessToken)
            }.let { ProcedureResult.ok(it) }
        }
    }

    @HttpPut("/custom")
    suspend fun putCustom(@PropertyValue accessToken: AccessToken, @StringBody custom: String): ProcedureResult {
        logger.debug("[api:self:preferences] PUT custom: {}, {}", custom, accessToken)
        return playerService.lock(accessToken.uid).use { lock ->
            playerService.autoRetry { retryCount ->
                val (ctx, player) = playerService.getFixedPlayerWithContext(accessToken)
                player.preferences.custom = custom
                playerService.update(ctx)
                ProcedureResultData.of(player, retryCount)
            }.also {
                logger.debug("[api:self:preferences] PUT custom result: {}, {}", it, accessToken)
            }.let { ProcedureResult.ok(it) }
        }
    }

}
