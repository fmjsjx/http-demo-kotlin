package com.github.fmjsjx.demo.http.common.util

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.JSONWriter.Feature.WriteMapNullValue

fun JSONObject.toJsonString(): String = toJSONString(WriteMapNullValue)