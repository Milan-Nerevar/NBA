package com.nerevar.nba.core.compose

import androidx.compose.runtime.Immutable

/**
 * UI state wrapper with support for loading via [isLoading] * error handling via [errorState].
 *
 * @see [ErrorScreen]
 * @see [LoadingScreen]
 */
@Immutable
data class UIState<out Data>(
    val data: Data,
    val isLoading: Boolean = true,
    val errorState: ErrorState? = null,
)

