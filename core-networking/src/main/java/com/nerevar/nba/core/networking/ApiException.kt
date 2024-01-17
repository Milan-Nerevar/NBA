package com.nerevar.nba.core.networking

import retrofit2.HttpException
import java.io.IOException

sealed class ApiException(cause: Exception): RuntimeException(cause)

/**
 * Server returned an error.
 */
class ServerException(cause: HttpException): ApiException(cause)

/**
 * Connection to server unsuccessful.
 */
class NetworkException(cause: IOException): ApiException(cause)

/**
 * An unknown error occurred while executing the call.
 */
class UnknownNetworkException(cause: Exception) : ApiException(cause)
