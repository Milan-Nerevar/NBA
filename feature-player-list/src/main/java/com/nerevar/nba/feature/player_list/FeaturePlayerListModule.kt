package com.nerevar.nba.feature.player_list

import com.nerevar.nba.feature.player_list.infrastructure.PlayerListNextPageUseCase
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListNextPageUseCaseImpl
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListReloadUseCase
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListReloadUseCaseImpl
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListRepository
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListRepositoryImpl
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListUseCase
import com.nerevar.nba.feature.player_list.infrastructure.PlayerListUseCaseImpl
import com.nerevar.nba.feature.player_list.presentation.PlayerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featurePlayerListModule = module {
    singleOf(::PlayerListRepositoryImpl) bind PlayerListRepository::class
    factoryOf(::PlayerListUseCaseImpl) bind PlayerListUseCase::class
    factoryOf(::PlayerListReloadUseCaseImpl) bind PlayerListReloadUseCase::class
    factoryOf(::PlayerListNextPageUseCaseImpl) bind PlayerListNextPageUseCase::class
    viewModelOf(::PlayerListViewModel)
}
