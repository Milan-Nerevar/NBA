package com.nerevar.nba.feature.player_detail.presentation

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.forward
import com.nerevar.nba.core.compose.ButtonState
import com.nerevar.nba.core.compose.Compose
import com.nerevar.nba.core.compose.ErrorScreen
import com.nerevar.nba.core.compose.LoadingScreen
import com.nerevar.nba.core.compose.NBATheme
import com.nerevar.nba.core.compose.PrimaryButton
import com.nerevar.nba.core.compose.ScreenPreview
import com.nerevar.nba.core.compose.UIState
import com.nerevar.nba.core.compose.getValue
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.compose.value
import com.nerevar.nba.core.navigation.NavDestination
import com.nerevar.nba.core.navigation.subscribeToNavCommand
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Parcelize
class PlayerDetailScreen(
    private val id: Int,
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<PlayerDetailViewModel> { parametersOf(id) }
        val navDestination = koinInject<NavDestination>()
        viewModel.navigationCommand.subscribeToNavCommand {
            when (it) {
                is PlayerDetailNavCommand.ClubDetail -> forward(
                    navDestination.clubDetailScreen(it.id)
                )
            }
        }
        val state = viewModel.state.collectAsStateWithLifecycle().value
        PlayerDetailScreenInternal(state)
    }
}

@Composable
private fun PlayerDetailScreenInternal(
    state: UIState<PlayerDetailState?>
) {
    val data = state.data
    val error = state.errorState
    if (data != null) {
        PlayerDetailData(
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
private fun PlayerDetailData(state: PlayerDetailState, modifier: Modifier = Modifier) {
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

                val context = LocalContext.current

                val label by remember {
                    derivedStateOf {
                        listOfNotNull(
                            state.club,
                            state.position,
                            state.weight,
                            state.height
                        ).joinToString(" | ") { it.getValue(context) }
                    }
                }

                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = state.name.value,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Text(
                    text = state.surname.value,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        PrimaryButton(
            state = state.clubButton,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@ScreenPreview
@Composable
private fun DataPreview() = NBATheme {
    PlayerDetailScreenInternal(
        state = UIState(
            data = PlayerDetailState(
                image = imageResource(Color.Black),
                name = stringResource("Name"),
                surname = stringResource("Surname"),
                position = stringResource("Position"),
                club = stringResource("Club"),
                height = stringResource("Height"),
                weight = stringResource("Weight"),
                clubButton = ButtonState(stringResource("Navigate to Club")) {}
            ),
            isLoading = false
        )
    )
}
