package com.nerevar.nba.core.compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Composable
fun PrimaryButton(state: ButtonState, modifier: Modifier = Modifier) {
    Button(
        onClick = state.onClick,
        modifier = modifier
    ) {
        Text(text = state.text.value)
    }
}

@Immutable
data class ButtonState(val text: StringResource, val onClick: () -> Unit)

@ComponentPreview
@Composable
private fun Preview() = NBATheme {
    PrimaryButton(
        state = ButtonState(
            text = stringResource("Primary button"),
        ) {}
    )
}
