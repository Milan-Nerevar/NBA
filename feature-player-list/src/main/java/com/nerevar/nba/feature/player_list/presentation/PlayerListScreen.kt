package com.nerevar.nba.feature.player_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.forward
import com.nerevar.nba.core.compose.ButtonState
import com.nerevar.nba.core.compose.ErrorScreen
import com.nerevar.nba.core.compose.ErrorState
import com.nerevar.nba.core.compose.LazyColumnPaginated
import com.nerevar.nba.core.compose.LoadingScreen
import com.nerevar.nba.core.compose.NBATheme
import com.nerevar.nba.core.compose.ScreenPreview
import com.nerevar.nba.core.compose.UIState
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.navigation.NavDestination
import com.nerevar.nba.core.navigation.subscribeToNavCommand
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Parcelize
class PlayerListScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<PlayerListViewModel>()
        val navDestination = koinInject<NavDestination>()
        viewModel.navigationCommand.subscribeToNavCommand {
            when (it) {
                is PlayerListNavCommand.PlayerDetail -> forward(
                    navDestination.playerDetailScreen(it.id)
                )
            }
        }
        val state = viewModel.state.collectAsStateWithLifecycle().value
        PlayerListScreenInternal(state)
    }
}

@Composable
private fun PlayerListScreenInternal(state: UIState<PlayerListState?>) {
    val data = state.data
    val error = state.errorState
    if (data != null) {
        PlayerListData(
            state = data,
            isLoading = state.isLoading,
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
private fun PlayerListData(
    state: PlayerListState,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumnPaginated(
        modifier = modifier,
        onRefresh = state.onScrolledToTheEnd
    ) {
        itemsIndexed(
            items = state.players,
            contentType = { _, item -> item.contentType }
        ) { index, item ->
            if (index != 0) {
                Divider(Modifier.fillMaxWidth())
            }
            PlayerRow(
                modifier = Modifier.fillMaxWidth(),
                state = item,
            )
        }
        if (isLoading) {
            item(key = "Loading") {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@ScreenPreview
@Composable
private fun DataPreview() = NBATheme {
    PlayerListScreenInternal(
        state = UIState(
            data = PlayerListState(
                players = listOf(
                    PlayerRowState(
                        image = imageResource(MaterialTheme.colorScheme.primary),
                        name = stringResource("LeBron"),
                        surname = stringResource("James"),
                        position = stringResource("F"),
                        club = stringResource("LAL"),
                    ) {},
                    PlayerRowState(
                        image = imageResource(MaterialTheme.colorScheme.primary),
                        name = stringResource("LeBron"),
                        surname = stringResource("James"),
                        position = stringResource("F"),
                        club = stringResource("LAL"),
                    ) {}, PlayerRowState(
                        image = imageResource(MaterialTheme.colorScheme.primary),
                        name = stringResource("LeBron"),
                        surname = stringResource("James"),
                        position = stringResource("F"),
                        club = stringResource("LAL"),
                    ) {}
                ),
                onScrolledToTheEnd = {}
            ),
            isLoading = true
        )
    )
}

@ScreenPreview
@Composable
private fun LoadingPreview() = NBATheme {
    PlayerListScreenInternal(
        state = UIState(
            data = null,
            isLoading = true
        )
    )
}

@ScreenPreview
@Composable
private fun ErrorPreview() = NBATheme {
    PlayerListScreenInternal(
        state = UIState(
            data = null,
            isLoading = true,
            errorState = ErrorState(
                stringResource("Title"),
                stringResource("Message"),
                ButtonState(
                    stringResource("Retry")
                ) {}
            )
        )
    )
}
