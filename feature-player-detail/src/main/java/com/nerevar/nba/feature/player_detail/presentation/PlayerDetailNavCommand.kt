package com.nerevar.nba.feature.player_detail.presentation

import com.nerevar.nba.core.navigation.NavCommand

internal sealed interface PlayerDetailNavCommand : NavCommand {
    data class ClubDetail(val id: Int) : PlayerDetailNavCommand
}
