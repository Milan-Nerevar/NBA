package com.nerevar.nba.feature.player_list.presentation

import com.nerevar.nba.core.navigation.NavCommand

internal sealed interface PlayerListNavCommand: NavCommand {
    data class PlayerDetail(val id: Int) : PlayerListNavCommand
}
