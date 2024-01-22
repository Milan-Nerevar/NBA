package com.nerevar.nba.core.api

import com.nerevar.nba.core.api.players.PlayerApi
import com.nerevar.nba.core.api.players.PlayerInteractor
import com.nerevar.nba.core.api.players.PlayerInteractorImpl
import com.nerevar.nba.core.networking.create
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val coreApiModule = module {
    single { Moshi.Builder().build() }
    single {
        val url = with(androidApplication()) {
            getString(R.string.base_url) + getString(R.string.api_path)
        }

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(url)
            .build()
    }
    single<PlayerApi> { get<Retrofit>().create() }
    singleOf(::PlayerInteractorImpl) bind PlayerInteractor::class
}
