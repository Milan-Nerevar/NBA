package com.nerevar.nba.core.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginationMetadata(
    @Json(name = "current_page") val currentPage: Int,
    @Json(name = "next_page") val nextPage: Int,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "per_page") val perPage: Int,
    @Json(name = "total_count") val totalCount: Int?
)
