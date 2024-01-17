package com.nerevar.nba.feature.player_detail

import com.nerevar.nba.feature.player_detail.infrastructure.PlayerDetailUseCase
import com.nerevar.nba.feature.player_detail.infrastructure.PlayerDetailUseCaseImpl
import com.nerevar.nba.feature.player_detail.presentation.PlayerDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featurePlayerDetailModule = module {
    factoryOf(::PlayerDetailUseCaseImpl) bind PlayerDetailUseCase::class
    viewModelOf(::PlayerDetailViewModel)
}
