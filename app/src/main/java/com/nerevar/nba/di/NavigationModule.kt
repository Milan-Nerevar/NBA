package com.nerevar.nba.di

import com.nerevar.nba.navigation.NavDestinationImpl
import com.nerevar.nba.core.navigation.NavDestination
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::NavDestinationImpl) bind NavDestination::class
}
