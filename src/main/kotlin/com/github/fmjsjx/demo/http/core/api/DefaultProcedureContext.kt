package com.github.fmjsjx.demo.http.core.api

import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.core.entity.model.Player
import java.time.LocalDateTime

class DefaultProcedureContext(
    override val accessToken: AccessToken,
    override var player: Player?,
    override val time: LocalDateTime,
) : ProcedureContext {

    override val events: MutableList<String> = ArrayList()

    override val hasEvents: Boolean
        get() = events.isNotEmpty()

    override infix fun hasEvent(event: String): Boolean = event in events

    override fun event(event: String): ProcedureContext = apply {
        if (event !in events) {
            events.add(event)
        }
    }

    private val properties: MutableMap<String, Any> = LinkedHashMap()

    override fun hasProperty(name: String): Boolean = name in properties

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> property(name: String): T? = properties[name]?.let { it as T }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> property(name: String, value: T): T? = properties.put(name, value)?.let { it as T }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> removeProperty(name: String): T? = properties.remove(name)?.let { it as T }

    override fun toString(): String =
        "DefaultProcedureContext(accessToken=$accessToken, player=$player, time=$time, events=$events, properties=$properties)"


}
