package com.nerevar.nba.core.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(state: ErrorState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = state.title.value,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )

        Text(
            text = state.message.value,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )

        PrimaryButton(
            state = state.retryButton,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Immutable
data class ErrorState(
    val title: StringResource,
    val message: StringResource,
    val retryButton: ButtonState
)

@ComponentPreview
@Composable
private fun ErrorScreenPreview() = NBATheme {
    ErrorScreen(
        modifier = Modifier.fillMaxSize(),
        state = ErrorState(
            stringResource("Title"),
            stringResource("Message"),
            ButtonState(
                stringResource("Retry")
            ) {}
        )
    )
}
