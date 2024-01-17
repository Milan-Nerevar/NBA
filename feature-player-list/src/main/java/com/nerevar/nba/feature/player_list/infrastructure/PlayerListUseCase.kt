package com.nerevar.nba.feature.player_list.infrastructure

import com.nerevar.nba.core.util.ExecutionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

internal interface PlayerListUseCase {
    operator fun invoke(): Flow<ExecutionResult<PlayerListData>>
}

internal class PlayerListUseCaseImpl(
    private val playerListRepository: PlayerListRepository
): PlayerListUseCase {
    override fun invoke(): Flow<ExecutionResult<PlayerListData>> {
        return channelFlow {
            launch {
                playerListRepository.players.collect {
                    send(it)
                }
            }
            playerListRepository.requestFirstPage()
        }
    }
}
