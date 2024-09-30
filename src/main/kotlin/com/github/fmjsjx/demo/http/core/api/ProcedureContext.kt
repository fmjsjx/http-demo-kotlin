package com.github.fmjsjx.demo.http.core.api

import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.core.entity.model.Player
import com.github.fmjsjx.libcommon.util.DateTimeUtil
import java.time.LocalDateTime

interface ProcedureContext {

    companion object {
        fun create(
            accessToken: AccessToken,
            player: Player? = null,
            time: LocalDateTime = DateTimeUtil.ofEpochMilli(System.currentTimeMillis()),
        ): ProcedureContext = DefaultProcedureContext(accessToken, player, time)
    }

    val accessToken: AccessToken
    var player: Player?
    val time: LocalDateTime
    val events: List<String>
    val hasEvents: Boolean
    infix fun hasEvent(event: String): Boolean
    fun event(event: String): ProcedureContext
    fun hasProperty(name: String): Boolean
    fun <T : Any> property(name: String): T?
    fun <T : Any> property(name: String, value: T): T?
    fun <T : Any> removeProperty(name: String): T?
    operator fun <T : Any> get(name: String): T? = property(name)
    operator fun <T : Any> set(name: String, value: T) {
        property(name, value)
    }

    operator fun contains(name: String): Boolean = hasProperty(name)

}
