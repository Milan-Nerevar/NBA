package com.nerevar.nba.core.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginationData<T>(
    @Json(name = "meta") val meta: PaginationMetadata,
    @Json(name = "data") val data: List<T>
)
