package com.nerevar.nba.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.github.terrakok.modo.LocalContainerScreen
import com.github.terrakok.modo.NavigationContainer
import com.github.terrakok.modo.stack.StackScreen
import com.github.terrakok.modo.stack.StackState
import kotlinx.coroutines.flow.Flow

interface NavCommand

@Composable
inline fun <reified T : NavCommand> Flow<T>.subscribeToNavCommand(
    crossinline collector: NavigationContainer<StackState>.(command: T) -> Unit
) {
    val navigatorScreen = LocalContainerScreen.current as StackScreen
    LaunchedEffect(Unit) {
        this@subscribeToNavCommand.collect {
            navigatorScreen.collector(it)
        }
    }
}
