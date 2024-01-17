package com.nerevar.nba.core.compose

import androidx.compose.runtime.Immutable

@Immutable
data class UIState<out Data>(
    val data: Data,
    val isLoading: Boolean = true,
    val errorState: ErrorState? = null,
)

