package com.nerevar.nba.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

sealed interface ExecutionResult<out T> {

    data object Loading : ExecutionResult<Nothing>

    data class Data<out T>(val data: T) : ExecutionResult<T>

    data class Error(val exception: Exception) : ExecutionResult<Nothing>
}

fun <T> executionFlow() = MutableStateFlow<ExecutionResult<T>>(ExecutionResult.Loading)

@Suppress("TooGenericExceptionCaught")
fun <T> execute(block: suspend () -> T): Flow<ExecutionResult<T>> {
    return flow {
        try {
            emit(ExecutionResult.Loading)
            emit(ExecutionResult.Data(block()))
        } catch (e: Exception) {
            emit(ExecutionResult.Error(e))
        }
    }
}

fun <T> Flow<ExecutionResult<T>>.onLoading(callback: suspend () -> Unit) =
    onEach {
        if (it is ExecutionResult.Loading) {
            callback()
        }
    }

fun <T> Flow<ExecutionResult<T>>.onSuccess(callback: suspend (T) -> Unit) =
    onEach {
        if (it is ExecutionResult.Data) {
            callback(it.data)
        }
    }

fun <T> Flow<ExecutionResult<T>>.onError(callback: suspend (Exception) -> Unit) =
    onEach {
        if (it is ExecutionResult.Error) {
            callback(it.exception)
        }
    }

fun <T> Flow<ExecutionResult<T>>.onEachSetTo(flow: MutableSharedFlow<ExecutionResult<T>>): Flow<ExecutionResult<T>> =
    onEach {
        flow.emit(it)
    }
