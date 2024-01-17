package com.nerevar.nba.feature.player_detail.infrastructure

import com.nerevar.nba.core.api.players.PlayerInteractor
import com.nerevar.nba.core.domain.PlayerDto

internal interface PlayerDetailUseCase {
    suspend operator fun invoke(id: Int): PlayerDto
}

internal class PlayerDetailUseCaseImpl(
    private val playerInteractor: PlayerInteractor
) : PlayerDetailUseCase {
    override suspend fun invoke(id: Int): PlayerDto = playerInteractor.getPlayerDetail(id)
}
