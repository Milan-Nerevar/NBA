package com.nerevar.nba.feature.player_list.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal data class PlayerListState(
    val players: List<PlayerRowState>,
    val onScrolledToTheEnd: () -> Unit,
)
