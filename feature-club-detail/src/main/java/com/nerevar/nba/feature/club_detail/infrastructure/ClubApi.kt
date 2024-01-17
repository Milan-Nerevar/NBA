package com.nerevar.nba.feature.club_detail.infrastructure

import com.nerevar.nba.core.domain.TeamDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ClubApi {
    @GET("teams/{id}")
    suspend fun getTeamDetail(@Path("id") id: Int): TeamDto
}
