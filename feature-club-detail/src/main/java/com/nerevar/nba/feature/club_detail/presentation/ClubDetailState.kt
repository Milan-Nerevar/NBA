package com.nerevar.nba.feature.club_detail.presentation

import androidx.compose.runtime.Immutable
import com.nerevar.nba.core.compose.ImageResource
import com.nerevar.nba.core.compose.StringResource

@Immutable
internal data class ClubDetailState(
    val image: ImageResource,
    val city: StringResource,
    val name: StringResource,
    val division: StringResource,
    val conference: StringResource,
)
