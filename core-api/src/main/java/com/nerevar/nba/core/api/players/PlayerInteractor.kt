package com.nerevar.nba.core.api.players

import com.nerevar.nba.core.domain.PaginationData
import com.nerevar.nba.core.domain.PlayerDto
import com.nerevar.nba.core.networking.RemoteInteractor

interface PlayerInteractor {
    suspend fun getPlayerDetail(id: Int): PlayerDto
    suspend fun getPlayers(page: Int): PaginationData<PlayerDto>
}

class PlayerInteractorImpl(
    private val playerApi: PlayerApi,
) : PlayerInteractor, RemoteInteractor by RemoteInteractor() {
    override suspend fun getPlayerDetail(id: Int): PlayerDto = networkCall {
        playerApi.getPlayerDetail(id)
    }

    override suspend fun getPlayers(page: Int): PaginationData<PlayerDto> = networkCall {
        playerApi.getPlayers(page)
    }
}
