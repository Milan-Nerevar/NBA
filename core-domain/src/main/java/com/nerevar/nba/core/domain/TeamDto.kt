package com.nerevar.nba.core.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDto(
    @Json(name = "id") val id: Int,
    @Json(name = "abbreviation") val abbreviation: String,
    @Json(name = "city") val city: String,
    @Json(name = "conference") val conference: String,
    @Json(name = "division") val division: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "name") val name: String
)
