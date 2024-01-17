package com.nerevar.nba.feature.club_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerevar.nba.core.compose.ButtonState
import com.nerevar.nba.core.compose.ErrorState
import com.nerevar.nba.core.compose.UIState
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.domain.PlayerDto
import com.nerevar.nba.core.domain.TeamDto
import com.nerevar.nba.core.resources.R
import com.nerevar.nba.core.util.ExecutionResult
import com.nerevar.nba.core.util.execute
import com.nerevar.nba.core.util.executionFlow
import com.nerevar.nba.core.util.onEachSetTo
import com.nerevar.nba.feature.club_detail.infrastructure.ClubDetailUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class ClubDetailViewModel(
    private val id: Int,
    private val loadClub: ClubDetailUseCase
) : ViewModel() {

    private val clubResult = executionFlow<TeamDto>()

    val state: StateFlow<UIState<ClubDetailState?>> = clubResult
        .map { result ->
            when (result) {
                is ExecutionResult.Data -> UIState(
                    data = ClubDetailState(
                        image = imageResource(HARD_CODED_URL),
                        city = stringResource(result.data.city),
                        name = stringResource(result.data.name),
                        division = stringResource(result.data.division),
                        conference = stringResource(result.data.conference),

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

    init {
        load()
    }

    private fun load() {
        execute { loadClub(id) }
            .onEachSetTo(clubResult)
            .launchIn(viewModelScope)
    }
}

private const val HARD_CODED_URL = "https://fastly.picsum.photos/id/64/4326/2884.jpg?hmac=9_SzX666YRpR_fOyYStXpfSiJ_edO3ghlSRnH2w09Kg"
