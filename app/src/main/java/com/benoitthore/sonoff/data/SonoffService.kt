package com.benoitthore.sonoff.data


import com.benoitthore.base.lib.data.Mapper
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SonoffService {
    @GET("cm?cmnd=Power")
    suspend fun test(): Response<ResponseBody>

    @GET("cm?cmnd=Power")
    suspend fun getPowerState(): Response<PowerResponseDTO>

    @GET("cm?cmnd=Power%20On")
    suspend fun setOn(): Response<PowerResponseDTO>

    @GET("cm?cmnd=Power%20Off")
    suspend fun setOff(): Response<PowerResponseDTO>

    @GET("cm?cmnd=Power%20Toggle")
    suspend fun toggle(): Response<PowerResponseDTO>

    @GET("cm?cmnd=Hostname%20{name}")
    suspend fun setHostName(@Path("name") name: String): Response<HostNameResponseDTO>

    @GET("cm?cmnd=Hostname")
    suspend fun getHostName(): Response<HostNameResponseDTO>

}

data class PowerResponseDTO(@JsonProperty("POWER") val power: String)

data class PowerData(val isOn: Boolean)

object PowerResponseMapper : Mapper<PowerResponseDTO, PowerData> {
    override fun invoke(dto: PowerResponseDTO) = PowerData(dto.power?.toLowerCase() == "on")
}


data class HostNameResponseDTO(@JsonProperty("Hostname") val hostname: String)

data class HostNameData(val name: String)

object HostNameResponseMapper : Mapper<HostNameResponseDTO, HostNameData> {
    override fun invoke(dto: HostNameResponseDTO) = HostNameData(dto.hostname)
}


data class SonoffDeviceId(val id: String)

fun String.asSonoffDevice() = SonoffDeviceId(this)

