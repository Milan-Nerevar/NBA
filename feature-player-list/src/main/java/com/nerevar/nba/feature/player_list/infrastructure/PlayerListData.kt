package com.nerevar.nba.feature.player_list.infrastructure

import com.nerevar.nba.core.domain.PlayerDto

internal data class PlayerListData(val players: List<PlayerDto>, val isLoading: Boolean)
