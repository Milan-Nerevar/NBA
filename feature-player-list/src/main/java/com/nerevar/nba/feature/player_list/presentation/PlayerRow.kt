package com.nerevar.nba.feature.player_list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nerevar.nba.core.compose.ComponentPreview
import com.nerevar.nba.core.compose.Compose
import com.nerevar.nba.core.compose.ImageResource
import com.nerevar.nba.core.compose.Itemizable
import com.nerevar.nba.core.compose.NBATheme
import com.nerevar.nba.core.compose.StringResource
import com.nerevar.nba.core.compose.imageResource
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.compose.value

@Composable
internal fun PlayerRow(state: PlayerRowState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable { state.onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(modifier = Modifier.weight(.5f)) {
            state.image.Compose(modifier = Modifier.size(48.dp), shape = CircleShape)

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = state.name.value,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = state.surname.value,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(
            modifier = Modifier.weight(.1f),
            text = state.club.value,
        )
        Text(
            modifier = Modifier
                .weight(.4f)
                .padding(start = 8.dp),
            text = state.position.value,
        )
    }
}

@Immutable
internal data class PlayerRowState(
    val image: ImageResource,
    val name: StringResource,
    val surname: StringResource,
    val position: StringResource,
    val club: StringResource,
    val onClick: () -> Unit,
) : Itemizable {
    override val contentType: Any = "Player"
}

@ComponentPreview
@Composable
private fun PlayerPreview() = NBATheme {
    PlayerRow(
        modifier = Modifier
            .fillMaxWidth(),
        state = PlayerRowState(
            image = imageResource(MaterialTheme.colorScheme.primary),
            name = stringResource("LeBron"),
            surname = stringResource("James"),
            position = stringResource("F"),
            club = stringResource("LAL"),
        ) {}
    )
}
