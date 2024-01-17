package com.nerevar.nba.feature.player_list.presentation

internal sealed interface PlayerListNavCommand {
    data class PlayerDetail(val id: Int) : PlayerListNavCommand
}
