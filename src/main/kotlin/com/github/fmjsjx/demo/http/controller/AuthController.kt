package com.github.fmjsjx.demo.http.controller

import com.github.fmjsjx.demo.http.api.BadRequestException
import com.github.fmjsjx.demo.http.api.ProcedureException
import com.github.fmjsjx.demo.http.api.ProcedureResult
import com.github.fmjsjx.demo.http.api.ProcedureResultData
import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.auth.AccessTokenService
import com.github.fmjsjx.demo.http.core.api.ProcedureContext
import com.github.fmjsjx.demo.http.core.entity.Account
import com.github.fmjsjx.demo.http.core.entity.Account.Companion.FORBIDDEN
import com.github.fmjsjx.demo.http.core.entity.model.Player
import com.github.fmjsjx.demo.http.core.service.AccountService
import com.github.fmjsjx.demo.http.core.service.PlayerService
import com.github.fmjsjx.demo.http.util.ConfigUtil
import com.github.fmjsjx.libcommon.util.StringUtil
import com.github.fmjsjx.libnetty.http.server.HttpRequestContext
import com.github.fmjsjx.libnetty.http.server.annotation.HttpPath
import com.github.fmjsjx.libnetty.http.server.annotation.HttpPost
import com.github.fmjsjx.libnetty.http.server.annotation.JsonBody
import com.github.fmjsjx.myboot.http.route.annotation.RouteController
import org.slf4j.LoggerFactory

@RouteController
@HttpPath("/api/v1/auth")
class AuthController(
    private val accountService: AccountService,
    private val accessTokenService: AccessTokenService,
    private val playerService: PlayerService,
) {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    @HttpPost("/guest/login")
    suspend fun postGuestLogin(ctx: HttpRequestContext, @JsonBody params: LoginParams): ProcedureResult {
        logger.debug("[api:auth] Guest login: {}, {}", params, ctx)
        params.apply {
            if (StringUtil.isBlank(udid)) {
                throw BadRequestException(400_101, "缺少必填参数`udid`")
            }
            if (StringUtil.isBlank(clientVersion)) {
                throw BadRequestException(400_102, "缺少必填参数`clientVersion`")
            }
        }
        val (newCreated, account) = accountService.guestAccount(params.udid!!) {
            Account().apply {
                remoteAddress = ctx.remoteAddress()!!
                clientVersion = params.clientVersion!!
                deviceInfo = params.deviceInfo ?: ""
                osInfo = params.osInfo ?: ""
            }
        }
        if (newCreated) {
            logger.debug("[api:auth] New account created: {}", account)
            // may log event "auth.account"
        }
        if (account.state == FORBIDDEN) {
            throw ProcedureException(201, "账号被禁用")
        }
        val loginInfo = params.toGuestLoginInfo(ctx.remoteAddress())
        val accessToken = accessTokenService.createOne(account, loginInfo)
        return playerService.lock(account.id, timeout = 15, maxWait = 60_000) { dataAccessError() }.use { lock ->
            var player = if (newCreated) {
                playerService.createGuestPlayer(accessToken)
            } else {
                playerService.getGuestPlayer(accessToken)
            }
            player = onPlayerLoggedIn(player, accessToken)
            playerService.cacheAsync(player)
            var result = LoginResult(
                uid = player.uid,
                accessToken = accessToken.id,
                registerTime = account.createdAt,
                slot = account.slot,
            ).apply {
                if (newCreated) {
                    newRegistered = 1
                }
            }
            ProcedureResultData(result = result).apply {
                sync = player.toData()
                force = 1
                logger.debug("[api:auth] Guest login result: {}", this)
            }.toProcedureResult()
        }
    }

    private fun dataAccessError() = ProcedureException(100, "数据访问异常")

    private suspend fun onPlayerLoggedIn(inputPlayer: Player, accessToken: AccessToken): Player {
        var player = inputPlayer
        (0..ConfigUtil.retryCount).forEach { retryCount ->
            var ctx = ProcedureContext.create(accessToken, player, accessToken.loginTime)
            playerService.fixPlayerBeforeProcessing(ctx)
            // may fix basic info from other platform user info
            // may fix features in future
            // fix login info
            player.login.apply {
                if (loginTime.isBefore(accessToken.loginTime)) {
                    increaseCount()
                    loginTime = accessToken.loginTime
                }
                ip = accessToken.remoteAddress
            }
            if (playerService.updateCas(ctx)) {
                logger.debug("[api:auth] Login player: {}", player)
                // may log event "auth.login"
                return player
            }
            if (retryCount < ConfigUtil.retryCount) {
                player = playerService.findOne(accessToken) ?: throw dataAccessError()
            }
        }
        logger.warn("[api:auth] Retry {} times failed when update player on logged in!", ConfigUtil.retryCount)
        throw dataAccessError()
    }

}

