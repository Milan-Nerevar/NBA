package com.nerevar.nba.core.api.players

import com.nerevar.nba.core.domain.PaginationData
import com.nerevar.nba.core.domain.PlayerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayerApi {
    @GET("players/{id}")
    suspend fun getPlayerDetail(@Path("id") id: Int): PlayerDto

    @GET("players")
    suspend fun getPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 35,
    ): PaginationData<PlayerDto>
}
