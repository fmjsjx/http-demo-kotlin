package com.github.fmjsjx.demo.http.core.entity.model

fun PreferencesInfo.includeFeature(feature: String): Boolean = feature in features

fun PreferencesInfo.excludeFeature(feature: String): Boolean = feature !in features
