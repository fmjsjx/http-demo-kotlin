package com.github.fmjsjx.demo.http.auth

import com.github.fmjsjx.libcommon.util.DateTimeUtil
import java.time.LocalDateTime

interface LoginInfo {
    val remoteAddress: String
    val clientVersion: String
    val udid: String
    val deviceInfo: String
    val osInfo: String
    val features: List<String>
    val time: LocalDateTime
    val unixTime: Long get() = DateTimeUtil.toEpochSecond(time)
}