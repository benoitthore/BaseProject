package com.benoitthore.sonoff.data

import com.benoitthore.base.lib.data.*

interface SonoffDeviceManager {

    suspend fun getState(): ApiResponse<PowerData>

    suspend fun toggle(): ApiResponse<PowerData>
    suspend fun turn(mode: Boolean): ApiResponse<PowerData>

    suspend fun turnOff() = turn(false)
    suspend fun turnOn() = turn(true)
}

class SonoffServiceBuilder : (SonoffDeviceId) -> SonoffService {
    override fun invoke(id: SonoffDeviceId): SonoffService = createService("http://${id.id}")
}


class SonoffDeviceManagerBuilder(
        private val serviceBuilder: SonoffServiceBuilder,
        private val dtoMapper: Mapper<PowerResponseDTO, PowerData>
) : (SonoffDeviceId) -> SonoffDeviceManager {
    override fun invoke(id: SonoffDeviceId): SonoffDeviceManager = SonoffDeviceManagerImpl(serviceBuilder(id), dtoMapper)

}

private class SonoffDeviceManagerImpl(val service: SonoffService, val dtoMapper: Mapper<PowerResponseDTO, PowerData>) : SonoffDeviceManager {
    override suspend fun getState(): ApiResponse<PowerData> = service.getPowerState().toApiResponse(dtoMapper)
    override suspend fun toggle(): ApiResponse<PowerData> = service.toggle().toApiResponse(dtoMapper)
    override suspend fun turn(mode: Boolean): ApiResponse<PowerData> = if (mode) service.setOn().toApiResponse(dtoMapper) else service.setOff().toApiResponse(dtoMapper)
}

interface SonoffRepository {
    val deviceList: List<SonoffDeviceManager>
    suspend fun getDeviceManager(id: SonoffDeviceId): SonoffDeviceManager?
    suspend fun addDeviceManager(id: SonoffDeviceId): SonoffDeviceManager?
    fun removeDevice(id: SonoffDeviceId): Boolean
}

// TODO Inject
class SonoffRepositoryImpl(private val deviceManagerBuilder: (SonoffDeviceId) -> SonoffDeviceManager = SonoffDeviceManagerBuilder(SonoffServiceBuilder(), PowerResponseMapper)) : SonoffRepository {

    private val map = mutableMapOf<SonoffDeviceId, SonoffDeviceManager>()

    private val _deviceList: MutableList<SonoffDeviceManager> = mutableListOf()
    override val deviceList: List<SonoffDeviceManager>
        get() = _deviceList

    override suspend fun getDeviceManager(id: SonoffDeviceId): SonoffDeviceManager? =
            deviceManagerBuilder(id).takeIf { it.isReachable() }

    override suspend fun addDeviceManager(id: SonoffDeviceId): SonoffDeviceManager? =
            getDeviceManager(id)?.also { deviceManager ->
                        map[id] = deviceManager
                    }

    override fun removeDevice(id: SonoffDeviceId): Boolean = map.remove(id) != null

    private suspend fun SonoffDeviceManager.isReachable(): Boolean = getState() !is ApiResponse.Success

}