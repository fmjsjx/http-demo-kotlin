package com.github.fmjsjx.demo.http.controller

import com.github.fmjsjx.demo.http.auth.LoginInfo
import java.time.LocalDateTime

data class LoginParams(
    var udid: String? = null,
    var clientVersion: String? = null,
    var deviceInfo: String? = null,
    var osInfo: String? = null,
    var features: List<String>? = null,
) {
    fun toGuestLoginInfo(remoteAddress: String, time: LocalDateTime = LocalDateTime.now()): GuestLoginInfo {
        return GuestLoginInfo(
            remoteAddress,
            clientVersion!!,
            udid!!,
            deviceInfo ?: "",
            osInfo ?: "",
            features ?: emptyList(),
            time,
        )
    }
}

data class GuestLoginInfo(
    override val remoteAddress: String,
    override val clientVersion: String,
    override val udid: String,
    override val deviceInfo: String,
    override val osInfo: String,
    override val features: List<String>,
    override val time: LocalDateTime,
) : LoginInfo

data class LoginResult(
    var uid: Long? = null,
    var accessToken: String? = null,
    var newRegistered: Int? = null,
    var registerTime: Long? = null,
    var slot: Int? = null,
)

