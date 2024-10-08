package com.github.fmjsjx.demo.http.util

import com.github.fmjsjx.demo.http.api.ItemBox
import com.github.fmjsjx.demo.http.auth.AccessToken
import com.github.fmjsjx.demo.http.core.api.ItemIds.COIN
import com.github.fmjsjx.demo.http.core.api.ItemIds.DIAMOND
import com.github.fmjsjx.demo.http.core.api.ItemIds.RANDOM_COIN
import com.github.fmjsjx.demo.http.core.api.ProcedureContext
import com.github.fmjsjx.demo.http.core.entity.model.Player

object ItemUtil {

    fun addItems(ctx: ProcedureContext, items: Collection<ItemBox>): Int = addItems(ctx.player!!, items)

    fun addItems(player: Player, items: Collection<ItemBox>): Int {
        for (item in items) {
            addItem(player, item)
        }
        return items.size
    }

    fun addItem(player: Player, item: ItemBox) {
        val id = item.item
        val num = item.num
        when (id) {
            COIN -> {
                val wallet = player.wallet
                val daily = player.daily
                val original = wallet.coin
                val coin = original + num
                val add = coin - original
                wallet.coinTotal += add
                daily.coin += add
            }

            DIAMOND -> {
                val wallet = player.wallet
                val daily = player.daily
                wallet.diamond += num
                daily.diamond += num
            }

            RANDOM_COIN -> throw IllegalArgumentException("item must be normalized")

            else -> {
                val items = player.items
                val original = items.get(id) ?: 0
                items.put(id, original + num)
            }
        }
    }

}
