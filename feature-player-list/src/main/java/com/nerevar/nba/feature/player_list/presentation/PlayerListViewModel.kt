package com.nerevar.nba.feature.player_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerevar.nba.core.compose.ButtonState
import com.nerevar.nba.core.compose.ErrorState
import com.nerevar.nba.core.compose.UIState
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.resources.R
import com.nerevar.nba.core.util.ExecutionResult
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListNextPageUseCase
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListReloadUseCase
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class PlayerListViewModel(
    private val playerList: PlayerListUseCase,
    private val reload: PlayerListReloadUseCase,
    private val nextPage: PlayerListNextPageUseCase
) : ViewModel() {

    val state: StateFlow<UIState<PlayerListState?>> = playerList()
        .map { result ->
            when (result) {
                is ExecutionResult.Data -> UIState(
                    data = PlayerListState(
                        players = result.data.players.map { player ->
                            PlayerRowState(
                                image = imageResource(HARD_CODED_URL),
                                name = stringResource(player.firstName),
                                surname = stringResource(player.lastName),
                                position = stringResource(player.position),
                                club = stringResource(player.team.name),
                            ) {
                                navigateToPlayerDetail(player.id)
                            }
                        },
                        onScrolledToTheEnd = { nextPage() }
                    ),
                    isLoading = result.data.isLoading
                )

                is ExecutionResult.Error -> UIState(
                    data = null,
                    isLoading = false,
                    errorState = ErrorState(
                        title = stringResource(R.string.error_title),
                        message = stringResource(R.string.error_message),
                        retryButton = ButtonState(
                            text = stringResource(R.string.general_retry),
                            onClick = { reload() }
                        )
                    )
                )

                ExecutionResult.Loading -> UIState(
                    data = null,
                    isLoading = true
                )
            }
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000, 0),
            UIState(data = null, isLoading = true)
        )

    private val _navigationCommand = MutableSharedFlow<PlayerListNavCommand>()
    val navigationCommand: SharedFlow<PlayerListNavCommand> = _navigationCommand

    private fun navigateToPlayerDetail(id: Int) = viewModelScope.launch {
        _navigationCommand.emit(PlayerListNavCommand.PlayerDetail(id))
    }
}

private const val HARD_CODED_URL =
    "https://fastly.picsum.photos/id/64/4326/2884.jpg?hmac=9_SzX666YRpR_fOyYStXpfSiJ_edO3ghlSRnH2w09Kg"

