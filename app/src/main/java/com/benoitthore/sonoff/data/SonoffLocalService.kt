package com.benoitthore.sonoff.data

import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private fun buildRequest(id: SonoffDeviceId, params: String? = null) =
        Request.Builder()
                .url("http://${id.id}/" + params?.let { "?$it" }.orEmpty())
                .build()

suspend fun OkHttpClient.asyncRequest(request: Request) = withContext(Dispatchers.IO) {
    newCall(request).execute()
}

interface LocalSonoffApiHelper {

    val GET_INFO_PARAMS get() = "m=1"
    val SWITCH_PARAMS get() = "m=1&o=1"

    fun isOn(response: String): Boolean
    class Impl : LocalSonoffApiHelper {
        override fun isOn(response: String): Boolean = response.contains("ON")

    }
}

class SonoffLocalService(val sonoffResponseParser: LocalSonoffApiHelper = LocalSonoffApiHelper.Impl()) : SonoffService {

    private val client = OkHttpClient.Builder().build()

    override suspend fun deviceExists(id: SonoffDeviceId): Boolean {
        val result = runCatching { client.asyncRequest(buildRequest(id)).code() == 200 }
        return result.isSuccess
    }

    override suspend fun getState(deviceId: SonoffDeviceId): SonoffResponse<Boolean> =
            doRequestWithParams(deviceId, sonoffResponseParser.GET_INFO_PARAMS)


    override suspend fun switch(deviceId: SonoffDeviceId): SonoffResponse<Boolean> =
            doRequestWithParams(deviceId, sonoffResponseParser.SWITCH_PARAMS)

    private suspend fun doRequestWithParams(id: SonoffDeviceId, params: String) =
            runCatching {
                client.asyncRequest(buildRequest(id, params))
                        .isOn()
            }.toSonoffResponse()

    private fun Response.isOn(): Boolean? = body()?.string()?.let {
        sonoffResponseParser.isOn(it)
    }
}

fun main() {
    runBlocking {
        val d1 = "192.168.1.144".asSonoffDevice()

        val repo = SonoffLocalRepo()

        repo.turnOn(d1)
    }
}