package com.github.fmjsjx.demo.http.auth

import com.alibaba.fastjson2.annotation.JSONField
import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.fmjsjx.demo.http.core.entity.Account
import java.time.LocalDate
import java.time.LocalDateTime

interface AccessToken {

    companion object {
        val KEY: Class<AccessToken> = AccessToken::class.java
    }

    val id: String
    val account: Account
    @get:JsonIgnore
    @get:com.jsoniter.annotation.JsonIgnore
    @get:JSONField(serialize = false)
    val uid: Long get() = account.id
    @get:JsonIgnore
    @get:com.jsoniter.annotation.JsonIgnore
    @get:JSONField(serialize = false)
    val groupId: Int get() = account.groupId
    @get:JsonIgnore
    @get:com.jsoniter.annotation.JsonIgnore
    @get:JSONField(serialize = false)
    val slot: Int get() = account.slot
    val remoteAddress: String
    val clientVersion: String
    val udid: String
    val deviceInfo: String
    val osInfo: String
    val loginTime: LocalDateTime
    val features: Set<String>
    @get:JsonIgnore
    @get:com.jsoniter.annotation.JsonIgnore
    @get:JSONField(serialize = false)
    val registerDate: LocalDate get() = account.createTime.toLocalDate()

    infix fun hasFeature(feature: String): Boolean {
        return feature in features
    }

}
