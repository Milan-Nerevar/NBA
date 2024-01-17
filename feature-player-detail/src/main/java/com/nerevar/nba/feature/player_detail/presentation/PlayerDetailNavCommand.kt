package com.nerevar.nba.feature.player_detail.presentation

internal sealed interface PlayerDetailNavCommand {
    data class ClubDetail(val id: Int) : PlayerDetailNavCommand
}
