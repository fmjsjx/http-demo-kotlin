package com.github.fmjsjx.demo.http.util

import com.github.fmjsjx.libcommon.util.SystemPropertyUtil

object ConfigUtil {

    val retryCount: Int = SystemPropertyUtil.getInt("api.retryCount", 3)

}
