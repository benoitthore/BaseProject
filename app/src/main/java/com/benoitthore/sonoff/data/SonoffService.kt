package com.benoitthore.sonoff.data

fun <T> Result<T?>.toSonoffResponse(): SonoffResponse<T> =
        getOrNull()?.let { SonoffResponse.Success(it) }
                ?: SonoffResponse.Error(exceptionOrNull())

sealed class SonoffResponse<T> {
    data class Success<T>(val value: T) : SonoffResponse<T>()
    data class Error<T>(val exception: Throwable? = null) : SonoffResponse<T>()
}

interface SonoffService {
    fun deviceExists(id: SonoffDeviceId): Boolean
    fun getState(deviceId: SonoffDeviceId): SonoffResponse<Boolean>

    fun switch(deviceId: SonoffDeviceId): SonoffResponse<Boolean>

}


data class SonoffDeviceId(val id: String)

fun String.asSonoffDevice() = SonoffDeviceId(this)