package com.benoitthore.sonoff.data

import com.benoitthore.base.lib.data.*
import java.net.InetAddress

interface SonoffDeviceManager {

    suspend fun getState(): ApiResponse<PowerData>

    suspend fun toggle(): ApiResponse<PowerData>
    suspend fun turn(mode: Boolean): ApiResponse<PowerData>

    suspend fun setHostName(hostName: String): ApiResponse<HostNameData>
    suspend fun getHostName(): ApiResponse<HostNameData>

    suspend fun turnOff() = turn(false)
    suspend fun turnOn() = turn(true)
}

class SonoffServiceBuilder : (SonoffDeviceId) -> SonoffService {
    override fun invoke(id: SonoffDeviceId): SonoffService = createService("http://${id.id}")
}


class SonoffDeviceManagerBuilder(
        private val serviceBuilder: SonoffServiceBuilder,
        private val powerResponseMapper: Mapper<PowerResponseDTO, PowerData>,
        private val hostNameResponseMapper: Mapper<HostNameResponseDTO, HostNameData>
) : (SonoffDeviceId) -> SonoffDeviceManager {
    override fun invoke(id: SonoffDeviceId): SonoffDeviceManager = SonoffDeviceManagerImpl(serviceBuilder(id), powerResponseMapper, hostNameResponseMapper)

}

private class SonoffDeviceManagerImpl(
        val service: SonoffService,
        val powerResponseMapper: Mapper<PowerResponseDTO, PowerData>,
        val hostNameResponseMapper: Mapper<HostNameResponseDTO, HostNameData>

) : SonoffDeviceManager {

    override suspend fun getState(): ApiResponse<PowerData> = service.getPowerState().toApiResponse(powerResponseMapper)
    override suspend fun toggle(): ApiResponse<PowerData> = service.toggle().toApiResponse(powerResponseMapper)
    override suspend fun turn(mode: Boolean): ApiResponse<PowerData> = if (mode) service.setOn().toApiResponse(powerResponseMapper) else service.setOff().toApiResponse(powerResponseMapper)
    override suspend fun setHostName(hostName: String): ApiResponse<HostNameData> = service.setHostName(hostName).toApiResponse(hostNameResponseMapper)
    override suspend fun getHostName(): ApiResponse<HostNameData> = service.getHostName().toApiResponse(hostNameResponseMapper)
}

interface SonoffRepository {
    val deviceList: List<SonoffDeviceManager>
    suspend fun getDeviceManager(id: SonoffDeviceId): SonoffDeviceManager?
    suspend fun addDeviceManager(id: SonoffDeviceId): SonoffDeviceManager?
    fun removeDevice(id: SonoffDeviceId): Boolean
}

// TODO Inject
// TODO handle get/set hostname
// TODO Scan network for hosts and return a map of HostNames to SonoffDeviceId (IP)
class SonoffRepositoryImpl(
        private val deviceManagerBuilder: (SonoffDeviceId) -> SonoffDeviceManager
        = SonoffDeviceManagerBuilder(SonoffServiceBuilder(), PowerResponseMapper, HostNameResponseMapper)
) : SonoffRepository {


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

fun main() {
    println(
            InetAddress.getByName("Sonoff1")
    )
}
