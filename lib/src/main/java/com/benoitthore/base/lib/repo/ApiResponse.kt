package com.benoitthore.base.lib.repo


import retrofit2.Response

sealed class ApiResponse<T> {
    class Success<T>(val value: T) : ApiResponse<T>()

    class ApiError<T>(val message: String) : ApiResponse<T>()

    class NetworkError<T>(val message: String) : ApiResponse<T>()
}

inline fun <I, O> Response<I>.toResult(mapper: Mapper<I, O>): ApiResponse<O> =
        if (isSuccessful) {
            body()
                    ?.let { ApiResponse.Success(mapper(it)) }
                    ?: ApiResponse.ApiError(message())
        } else {
            ApiResponse.NetworkError("Unknown error")
        }
