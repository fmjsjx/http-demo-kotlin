package com.github.fmjsjx.demo.http.api

import com.github.fmjsjx.demo.http.core.api.ProcedureContext
import com.github.fmjsjx.demo.http.core.entity.model.Player

data class ProcedureResultData(
    var result: Any? = null,
    var sync: Any? = null,
    var force: Int? = null,
    var del: Any? = null,
    var events: List<String>? = null,
) {

    fun fix(player: Player, retryCount: Int): ProcedureResultData {
        if (retryCount > 0) {
            sync = player.toData()
            force = 1
        } else if (player.anyUpdated()) {
            sync = player.toUpdateData()
            player.toDeletedData()?.let { del = it }
        }
        return this
    }

    fun events(ctx: ProcedureContext): ProcedureResultData {
        events = ctx.takeIf { it.hasEvents }?.events
        return this
    }

    fun appendEvent(event: String): ProcedureResultData {
        var list = this.events
        if (list == null) {
            this.events = listOf(event)
        } else {
            if (list is MutableList<String>) {
                list.add(event)
            } else {
                this.events = list + event
            }
        }
        return this
    }

    fun appendEvents(events: Collection<String>): ProcedureResultData {
        var list = this.events
        if (list == null) {
            this.events = events.toMutableList()
        } else {
            if (list is MutableList<String>) {
                list.addAll(events)
            } else {
                this.events = list + events
            }
        }
        return this
    }

    fun appendEvents(ctx: ProcedureContext): ProcedureResultData {
        if (ctx.hasEvents) {
            return appendEvents(ctx.events)
        }
        return this
    }

    companion object {

        fun of(result: Any, player: Player, retryCount: Int): ProcedureResultData =
            ProcedureResultData(result = result).fix(player, retryCount)

        fun of(player: Player, retryCount: Int): ProcedureResultData =
            ProcedureResultData().fix(player, retryCount)

    }

}