package com.nerevar.nba.feature.player_list.infrastructure

internal interface PlayerListReloadUseCase {
    operator fun invoke()
}

internal class PlayerListReloadUseCaseImpl(
    private val playerListRepository: PlayerListRepository
): PlayerListReloadUseCase {
    override fun invoke() {
        playerListRepository.reload()
    }
}
