package com.github.fmjsjx.demo.http.core.entity.model

import com.github.fmjsjx.demo.http.core.entity.model.Player.BNAME_UID
import com.github.fmjsjx.demo.http.core.entity.model.Player.BNAME_UPDATE_VERSION
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.combine
import org.bson.conversions.Bson

fun Player.toUpdateFilter(): Bson = and(eq(BNAME_UID, uid), eq(BNAME_UPDATE_VERSION, updateVersion))

fun Player.toBsonUpdate(): Bson = combine(toUpdates())
