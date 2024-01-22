package com.nerevar.nba.feature.player_detail.presentation

import androidx.compose.runtime.Immutable
import com.nerevar.nba.core.compose.ButtonState
import com.nerevar.nba.core.compose.ImageResource
import com.nerevar.nba.core.compose.StringResource

@Immutable
internal data class PlayerDetailState(
    val image: ImageResource,
    val name: StringResource,
    val surname: StringResource,
    val position: StringResource?,
    val height: StringResource?,
    val weight: StringResource?,
    val club: StringResource,
    val clubButton: ButtonState,
)
