package com.nerevar.nba.core.navigation

import androidx.compose.runtime.Composable
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.stack.StackNavModel
import com.github.terrakok.modo.stack.StackScreen
import kotlinx.parcelize.Parcelize

@Parcelize
class RootScreen(private val rootScreen: Screen) : StackScreen(StackNavModel(rootScreen)) {

    @Composable
    override fun Content() = TopScreenContent()
}
