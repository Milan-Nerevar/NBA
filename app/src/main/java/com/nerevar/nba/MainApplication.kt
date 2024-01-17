package com.nerevar.nba

import android.app.Application
import com.nerevar.nba.core.api.di.coreApiModule
import com.nerevar.nba.di.navigationModule
import com.nerevar.nba.feature.club_detail.featureClubDetailModule
import com.nerevar.nba.feature.player_detail.featurePlayerDetailModule
import com.nerevar.nba.feature.player_list.featurePlayerListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                navigationModule,
                coreApiModule,
                featureClubDetailModule,
                featurePlayerDetailModule,
                featurePlayerListModule
            )
        }
    }
}
