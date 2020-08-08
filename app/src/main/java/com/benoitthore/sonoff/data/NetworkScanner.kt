package com.benoitthore.sonoff.data

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.wifi.WifiManager
import android.text.format.Formatter
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.util.toAndroidPair
import kotlinx.coroutines.*
import java.net.InetAddress
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

// TODO Inject
class GetGatewayIpAddress(private val context: Context, val isConnectedToWifi: () -> Boolean = IsConnectedToWifi(context)) : () -> String? {
    override fun invoke(): String? {
        if (!isConnectedToWifi()) return null
        val wifiMgr = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wifiMgr.dhcpInfo.gateway)
    }

}

class NetworkScannerImpl(val deviceManagerBuilder: (SonoffDeviceId) -> SonoffDeviceManager
                         = SonoffDeviceManagerBuilder(SonoffServiceBuilder(), PowerResponseMapper, HostNameResponseMapper),
                         private val GetGatewayIpAddress: () -> String) : NetworkScanner {

    override suspend fun scan(): List<SonoffDeviceId> {
        val base = GetGatewayIpAddress().let { it.substring(0, it.lastIndexOf('.') + 1) }
        return coroutineScope {
            (0..255).asSequence()
                    .map { "$base$it" }
                    .map {
                        val id = it.asSonoffDevice()
                        async {
                            withTimeoutOrNull(1000L){
                                id to testIp(id)
                            }
                        }
                    }
                    .toList()
                    .awaitAll()
                    .filterNotNull()
                    .filter { (_, isReachable) -> isReachable }
                    .map { it.first }
        }
    }

    private suspend fun testIp(id: SonoffDeviceId): Boolean = deviceManagerBuilder(id).isReachable()
}

fun main() {
    runBlocking<Unit> {
        NetworkScannerImpl { "192.168.1.1" }.scan().forEach {
            println("Found Sonoff $it")
        }
        println("Scan done")
        exitProcess(0)
    }
}