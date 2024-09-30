package com.github.fmjsjx.demo.http.auth

import com.alibaba.fastjson2.annotation.JSONField
import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.fmjsjx.demo.http.core.entity.Account
import com.github.fmjsjx.libcommon.util.DateTimeUtil
import com.jsoniter.annotation.JsonCreator
import java.time.LocalDateTime

class AccessTokenImpl(
    override val id: String,
    override val account: Account,
    override val remoteAddress: String,
    override val clientVersion: String,
    override val udid: String,
    override val deviceInfo: String,
    override val osInfo: String,
    @JsonIgnore
    @JSONField(serialize = false)
    @com.jsoniter.annotation.JsonIgnore
    override val loginTime: LocalDateTime,
    override val features: LinkedHashSet<String>,
    val loggedInAt: Long = DateTimeUtil.toEpochMilli(loginTime)
) : AccessToken {

    @Suppress("unused")
    @JsonCreator
    constructor(
        id: String,
        account: Account,
        remoteAddress: String,
        clientVersion: String,
        udid: String,
        deviceInfo: String,
        osInfo: String,
        loggedInAt: Long,
        features: LinkedHashSet<String>,
    ) : this(
        id = id,
        account = account,
        remoteAddress = remoteAddress,
        clientVersion = clientVersion,
        udid = udid,
        deviceInfo = deviceInfo,
        osInfo = osInfo,
        loginTime = DateTimeUtil.ofEpochMilli(loggedInAt),
        features = features,
        loggedInAt = loggedInAt,
    )

    override fun toString(): String =
        "AccessTokenImpl(id=$id, account=$account, remoteAddress=$remoteAddress, clientVersion=$clientVersion, udid=$udid, deviceInfo=$deviceInfo, osInfo=$osInfo, loginTime=$loginTime, features=$features)"

}
