package com.nerevar.nba.feature.club_detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.nerevar.nba.core.compose.Compose
import com.nerevar.nba.core.compose.ErrorScreen
import com.nerevar.nba.core.compose.LoadingScreen
import com.nerevar.nba.core.compose.NBATheme
import com.nerevar.nba.core.compose.ScreenPreview
import com.nerevar.nba.core.compose.UIState
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.compose.value
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class ClubDetailScreen(
    private val id: Int,
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content() {
        val viewModel =
            koinViewModel<ClubDetailViewModel>(key = "ClubDetailScreen_$id") { parametersOf(id) }
        val state = viewModel.state.collectAsStateWithLifecycle().value
        ClubDetailScreenInternal(state)
    }
}

@Composable
private fun ClubDetailScreenInternal(state: UIState<ClubDetailState?>) {
    val data = state.data
    val error = state.errorState
    if (data != null) {
        ClubDetailData(
            state = data,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }

    if (data == null && state.isLoading) {
        LoadingScreen(modifier = Modifier.fillMaxSize())
    }

    if (error != null) {
        ErrorScreen(modifier = Modifier.fillMaxSize(), state = error)
    }
}

@Composable
private fun ClubDetailData(state: ClubDetailState, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            state.image.Compose(
                modifier = Modifier
                    .size(126.dp)
                    .align(Alignment.Bottom)
            )
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "${state.conference.value} ${state.division.value}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = state.city.value,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Text(
                    text = state.name.value,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@ScreenPreview
@Composable
private fun DataPreview() = NBATheme {
    ClubDetailScreenInternal(
        state = UIState(
            data = ClubDetailState(
                image = imageResource(Color.Black),
                city = stringResource("Los Angeles"),
                name = stringResource("Lakers"),
                division = stringResource("Pacific"),
                conference = stringResource("West"),
            ),
            isLoading = false
        )
    )
}
