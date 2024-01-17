package com.nerevar.nba.feature.club_detail

import com.nerevar.nba.core.networking.create
import com.nerevar.nba.feature.club_detail.infrastructure.ClubApi
import com.nerevar.nba.feature.club_detail.infrastructure.ClubDetailUseCase
import com.nerevar.nba.feature.club_detail.infrastructure.ClubDetailUseCaseImpl
import com.nerevar.nba.feature.club_detail.infrastructure.ClubInteractor
import com.nerevar.nba.feature.club_detail.infrastructure.ClubInteractorImpl
import com.nerevar.nba.feature.club_detail.presentation.ClubDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val featureClubDetailModule = module {
    single<ClubApi> { get<Retrofit>().create() }
    factoryOf(::ClubInteractorImpl) bind ClubInteractor::class
    factoryOf(::ClubDetailUseCaseImpl) bind ClubDetailUseCase::class
    viewModelOf(::ClubDetailViewModel)
}
