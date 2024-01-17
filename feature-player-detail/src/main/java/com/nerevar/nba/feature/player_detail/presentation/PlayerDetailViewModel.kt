package com.nerevar.nba.feature.player_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerevar.nba.core.compose.ButtonState
import com.nerevar.nba.core.compose.ErrorState
import com.nerevar.nba.core.compose.UIState
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.domain.PlayerDto
import com.nerevar.nba.core.resources.R
import com.nerevar.nba.core.util.ExecutionResult
import com.nerevar.nba.core.util.execute
import com.nerevar.nba.core.util.executionFlow
import com.nerevar.nba.core.util.onEachSetTo
import com.nerevar.nba.feature.player_detail.R.string.button_navigate_to_club
import com.nerevar.nba.feature.player_detail.infrastructure.PlayerDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class PlayerDetailViewModel(
    private val id: Int,
    private val loadPlayer: PlayerDetailUseCase,
) : ViewModel() {

    private val playerResult = executionFlow<PlayerDto>()

    val state: StateFlow<UIState<PlayerDetailState?>> = playerResult
        .map { result ->
            when (result) {
                is ExecutionResult.Data -> UIState(
                    data = PlayerDetailState(
                        image = imageResource(HARD_CODED_URL),
                        name = stringResource(result.data.firstName),
                        surname = stringResource(result.data.lastName),
                        position = stringResource(result.data.position),
                        club = stringResource(result.data.team.name),
                        height = stringResource(
                            listOfNotNull(
                                result.data.heightFeet,
                                result.data.heightInches
                            ).joinToString(" ")
                        ),
                        weight = result.data.weightPounds?.let { stringResource(it) },
                        clubButton = ButtonState(stringResource(button_navigate_to_club))
                        {
                            navigateToClubDetail(result.data.team.id)
                        }
                    ),
                    isLoading = false
                )

                is ExecutionResult.Error -> UIState(
                    data = null,
                    isLoading = false,
                    errorState = ErrorState(
                        title = stringResource(R.string.error_title),
                        message = stringResource(R.string.error_message),
                        retryButton = ButtonState(
                            text = stringResource(R.string.general_retry),
                            onClick = ::load
                        )
                    )
                )

                ExecutionResult.Loading -> UIState(
                    data = null,
                    isLoading = true
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000, 0),
            UIState(data = null, isLoading = true)
        )

    private val _navigationCommand = MutableSharedFlow<PlayerDetailNavCommand>()
    val navigationCommand: SharedFlow<PlayerDetailNavCommand> = _navigationCommand

    init {
        load()
    }

    private fun navigateToClubDetail(id: Int) = viewModelScope.launch {
        _navigationCommand.emit(PlayerDetailNavCommand.ClubDetail(id))
    }

    private fun load() {
        execute { loadPlayer(id) }
            .onEachSetTo(playerResult)
            .launchIn(viewModelScope)
    }
}

private const val HARD_CODED_URL = "https://fastly.picsum.photos/id/64/4326/2884.jpg?hmac=9_SzX666YRpR_fOyYStXpfSiJ_edO3ghlSRnH2w09Kg"
