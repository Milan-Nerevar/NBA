package com.nerevar.nba.feature.club_detail.infrastructure

import com.nerevar.nba.core.domain.TeamDto

internal interface ClubDetailUseCase {
    suspend operator fun invoke(id: Int): TeamDto
}

internal class ClubDetailUseCaseImpl(
    private val clubInteractor: ClubInteractor
) : ClubDetailUseCase {
    override suspend fun invoke(id: Int): TeamDto = clubInteractor.getTeamDetail(id)
}
