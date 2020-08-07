package com.benoitthore.sonoff.data

fun <T> Result<T?>.toSonoffResponse(): SonoffResponse<T> =
        getOrNull()?.let { SonoffResponse.Success(it) }
                ?: SonoffResponse.Error()

sealed class SonoffResponse<T> {
    data class Success<T>(val value: T) : SonoffResponse<T>()
    class Error<T> : SonoffResponse<T>()
}

interface SonoffService {
    suspend fun deviceExists(id: SonoffDeviceId): Boolean
    suspend fun getState(deviceId: SonoffDeviceId): SonoffResponse<Boolean>

    suspend fun switch(deviceId: SonoffDeviceId): SonoffResponse<Boolean>

}


data class SonoffDeviceId(val id: String)

fun String.asSonoffDevice() = SonoffDeviceId(this)