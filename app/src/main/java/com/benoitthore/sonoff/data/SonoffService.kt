package com.benoitthore.sonoff.data


import com.benoitthore.base.lib.data.Mapper
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

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
}

data class PowerResponseDTO(val POWER: String? = null)

data class PowerData(val isOn: Boolean)

object PowerResponseMapper : Mapper<PowerResponseDTO, PowerData> {
    override fun invoke(dto: PowerResponseDTO) = PowerData(dto.POWER?.toLowerCase() == "on")
}


data class SonoffDeviceId(val id: String)

fun String.asSonoffDevice() = SonoffDeviceId(this)

