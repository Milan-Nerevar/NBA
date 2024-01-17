package com.nerevar.nba.navigation

import com.github.terrakok.modo.Screen
import com.nerevar.nba.core.navigation.NavDestination
import com.nerevar.nba.feature.club_detail.presentation.ClubDetailScreen
import com.nerevar.nba.feature.player_detail.presentation.PlayerDetailScreen

class NavDestinationImpl: NavDestination {

    override fun playerDetailScreen(id: Int): Screen = PlayerDetailScreen(id)

    override fun clubDetailScreen(id: Int): Screen = ClubDetailScreen(id)
}
