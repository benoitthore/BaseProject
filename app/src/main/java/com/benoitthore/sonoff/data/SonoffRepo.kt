package com.benoitthore.sonoff.data

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.lang.Error
import kotlin.coroutines.suspendCoroutine

interface SonoffRepo {
    suspend fun addDevice(sonoffDeviceId: SonoffDeviceId)

    suspend fun getState(): Map<SonoffDeviceId, SonoffResponse<Boolean>>
    suspend fun getState(deviceId: SonoffDeviceId): SonoffResponse<Boolean>

    suspend fun switch(sonoffDeviceId: SonoffDeviceId): SonoffResponse<Boolean>

    suspend fun turn(sonoffDeviceId: SonoffDeviceId, mode: Boolean): SonoffResponse<Boolean> {
        val stateResponse = getState(sonoffDeviceId)
        if (stateResponse !is SonoffResponse.Success) {
            return SonoffResponse.Error()
        }
        if (stateResponse.value != mode) {
            return switch(sonoffDeviceId)
        }
        return SonoffResponse.Success(mode)
    }

    suspend fun turnOff(sonoffDeviceId: SonoffDeviceId): SonoffResponse<Boolean> = turn(sonoffDeviceId, false)
    suspend fun turnOn(sonoffDeviceId: SonoffDeviceId): SonoffResponse<Boolean> = turn(sonoffDeviceId, true)
}

class SonoffLocalRepo(private val service: SonoffService = SonoffLocalService()) : SonoffRepo {
    private val devices = mutableListOf<SonoffDeviceId>()

    override suspend fun addDevice(sonoffDeviceId: SonoffDeviceId) {
        devices += sonoffDeviceId
    }

    override suspend fun getState(): Map<SonoffDeviceId, SonoffResponse<Boolean>> = coroutineScope {
        devices
                .map { deviceId -> async { deviceId to getState(deviceId) } }
                .awaitAll()
                .toMap()
    }

    override suspend fun getState(deviceId: SonoffDeviceId) = service.getState(deviceId)

    override suspend fun switch(sonoffDeviceId: SonoffDeviceId): SonoffResponse<Boolean> =
            service.switch(sonoffDeviceId)

}