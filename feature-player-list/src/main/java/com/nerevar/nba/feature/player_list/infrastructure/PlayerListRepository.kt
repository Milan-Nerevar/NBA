package com.nerevar.nba.feature.player_list.infrastructure

import com.nerevar.nba.core.api.players.PlayerInteractor
import com.nerevar.nba.core.util.ExecutionResult
import com.nerevar.nba.core.util.execute
import com.nerevar.nba.core.util.executionFlow
import com.nerevar.nba.core.util.onError
import com.nerevar.nba.core.util.onLoading
import com.nerevar.nba.core.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal interface PlayerListRepository {
    val players: MutableStateFlow<ExecutionResult<PlayerListData>>
    fun reload()
    fun requestNextPage()
    fun requestFirstPage()
}

internal class PlayerListRepositoryImpl(
    private val playerInteractor: PlayerInteractor,
) : PlayerListRepository {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var currentPageIndex = 0
    private var isLastPage = false
    private val mutex = Mutex()

    override val players = executionFlow<PlayerListData>()

    override fun reload() {
        scope.coroutineContext.cancelChildren()
        requestPage(Command.RELOAD)
    }

    override fun requestNextPage() {
        requestPage(Command.NEXT_PAGE)
    }

    override fun requestFirstPage() {
        requestPage(Command.FIRST_PAGE)
    }

    private fun requestPage(command: Command) = scope.launch {
        mutex.withLock {
            val pageIndex = when (command) {
                Command.RELOAD -> {
                    players.update { ExecutionResult.Loading }
                    currentPageIndex = 0
                    isLastPage = false
                    0
                }

                Command.NEXT_PAGE -> {
                    if (isLastPage) return@withLock
                    currentPageIndex + 1
                }

                Command.FIRST_PAGE -> {
                    if (currentPageIndex > 0 || isLastPage) return@withLock
                    isLastPage = false
                    currentPageIndex + 1
                }
            }

            execute { playerInteractor.getPlayers(pageIndex) }
                .onLoading {
                    val new = when (val currentData = players.value) {
                        is ExecutionResult.Data -> ExecutionResult.Data(
                            currentData.data.copy(isLoading = true)
                        )

                        is ExecutionResult.Error -> ExecutionResult.Loading
                        ExecutionResult.Loading -> ExecutionResult.Loading
                    }

                    players.update { new }
                }
                .onError { error ->
                    currentPageIndex = 0
                    isLastPage = false
                    players.update { ExecutionResult.Error(error) }
                }
                .onSuccess { result ->
                    val new = when (val currentData = players.value) {
                        is ExecutionResult.Data -> ExecutionResult.Data(
                            currentData.data.copy(
                                players = currentData.data.players + result.data,
                                isLoading = false
                            )
                        )
                        else -> ExecutionResult.Data(
                            PlayerListData(
                                players = result.data,
                                isLoading = false
                            )
                        )
                    }

                    isLastPage = result.meta.currentPage == result.meta.totalPages
                    currentPageIndex = result.meta.currentPage
                    players.update { new }
                }
                .collect()
        }
    }
}

private enum class Command { RELOAD, NEXT_PAGE, FIRST_PAGE }

