package com.nerevar.nba.feature.player_list.infrastructure

internal interface PlayerListNextPageUseCase {
    operator fun invoke()
}

internal class PlayerListNextPageUseCaseImpl(
    private val playerListRepository: PlayerListRepository
): PlayerListNextPageUseCase {
    override fun invoke() {
        playerListRepository.requestNextPage()
    }
}
