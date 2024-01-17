package com.nerevar.nba.feature.club_detail.infrastructure

import com.nerevar.nba.core.domain.TeamDto
import com.nerevar.nba.core.networking.RemoteInteractor

internal interface ClubInteractor {
    suspend fun getTeamDetail(id: Int): TeamDto
}

internal class ClubInteractorImpl(
    private val clubApi: ClubApi
) : ClubInteractor, RemoteInteractor by RemoteInteractor() {
    override suspend fun getTeamDetail(id: Int): TeamDto = networkCall { clubApi.getTeamDetail(id) }
}
