package com.nerevar.nba.core.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

interface RemoteInteractor {
    companion object Factory {
        operator fun invoke(): RemoteInteractor = RemoteInteractorImpl()
    }

    @Throws(ServerException::class, NetworkException::class, UnknownNetworkException::class)
    suspend fun <T> networkCall(block: suspend () -> T): T
}

private class RemoteInteractorImpl : RemoteInteractor {
    override suspend fun <T> networkCall(block: suspend () -> T): T = withContext(Dispatchers.IO) {
        try {
            block()
        } catch (e: HttpException) {
            throw ServerException(e)
        } catch (e: IOException) {
            throw NetworkException(e)
        } catch (e: Exception) {
            throw UnknownNetworkException(e)
        }
    }
}
