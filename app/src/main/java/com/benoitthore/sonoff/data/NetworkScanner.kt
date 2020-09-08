package com.benoitthore.sonoff.data

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.wifi.WifiManager
import android.text.format.Formatter
import com.benoitthore.base.lib.data.ApiResponse
import kotlinx.coroutines.*
import kotlin.system.exitProcess


interface NetworkScanner {
    suspend fun scan(): List<SonoffDeviceId>
}

class IsConnectedToWifi(val context: Context) : () -> Boolean {
    override fun invoke(): Boolean {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connManager.allNetworks.forEach {
            if (connManager.getNetworkCapabilities(it).hasTransport(TRANSPORT_WIFI)) {
                return true
            }
        }
        return false
    }

}

class GetGatewayIpAddress(private val context: Context, val isConnectedToWifi: () -> Boolean) : () -> String? {
    override fun invoke(): String? {
        if (!isConnectedToWifi()) return null
        val wifiMgr = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wifiMgr.dhcpInfo.gateway)
    }

}

class NetworkScannerImpl(val deviceManagerBuilder: DeviceManagerBuilder,
                         private val getGatewayIpAddress: () -> String) : NetworkScanner {

    override suspend fun scan(): List<SonoffDeviceId> {
        val base = getGatewayIpAddress().let { it.substring(0, it.lastIndexOf('.') + 1) }

        return supervisorScope {
            (0..255).asSequence()
                    .map { testIp("$base$it") }
                    .toList()
                    .awaitAll()
                    .filterNotNull()
                    .filter { (_, isReachable) -> isReachable }
                    .map { it.first }
        }
    }

    private fun CoroutineScope.testIp(it: String): Deferred<Pair<SonoffDeviceId, Boolean>?> {
        val id = it.asSonoffDevice()
        return async {
            withTimeoutOrNull(1000L) {
                id to deviceManagerBuilder(id).isReachable()
            }
        }
    }

}

fun main() {
    runBlocking<Unit> {
        NetworkScannerImpl(object : DeviceManagerBuilder {
            override fun invoke(p1: SonoffDeviceId) = object : SonoffDeviceManager{
                override suspend fun getState(): ApiResponse<PowerData> {
                    TODO("Not yet implemented")
                }

                override suspend fun toggle(): ApiResponse<PowerData> {
                    TODO("Not yet implemented")
                }

                override suspend fun turn(mode: Boolean): ApiResponse<PowerData> {
                    TODO("Not yet implemented")
                }

                override suspend fun setHostName(hostName: String): ApiResponse<HostNameData> {
                    TODO("Not yet implemented")
                }

                override suspend fun getHostName(): ApiResponse<HostNameData> {
                    TODO("Not yet implemented")
                }
            }
        }) { "192.168.1.1" }.scan().forEach {

            // TODO Fix this
            println("Found Sonoff $it")
        }
        println("Scan done")
        exitProcess(0)
    }
}