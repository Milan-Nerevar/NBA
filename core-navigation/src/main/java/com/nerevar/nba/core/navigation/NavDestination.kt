package com.nerevar.nba.core.navigation

import com.github.terrakok.modo.Screen

/**
 * A factory for shared destination screens.
 */
interface NavDestination {
    fun playerDetailScreen(id: Int): Screen
    fun clubDetailScreen(id: Int): Screen
}
