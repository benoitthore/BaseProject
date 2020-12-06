package com.benoitthore.sonoff.data

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.wifi.WifiManager
import android.text.format.Formatter
import com.benoitthore.github.sonoffMacOsModule
import com.benoitthore.github.sonoffModule
import com.benoitthore.sonoff.replaceAll
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.inject
import kotlin.system.exitProcess


interface NetworkScanner {
    suspend fun scan(gatewayIpAddress: String): List<SonoffDeviceId>
}

interface IsConnectedToWifi : () -> Boolean

class IsConnectedToWifiDesktop() : IsConnectedToWifi {
    override fun invoke(): Boolean = true

}

class IsConnectedToWifiAndroid(val context: Context) : IsConnectedToWifi {
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

interface GetGatewayIpAddress {
    suspend operator fun invoke(): String?
}

class GetGatewayIpAddressMacOs : GetGatewayIpAddress {
    override suspend fun invoke(): String? = withContext(Dispatchers.IO) {
        val lines = Runtime.getRuntime().exec("traceroute -m 1 google.com").inputStream.bufferedReader().readLines()

        // Uncomment to stop crashes
//        if (lines.isEmpty()) {
//            return@withContext null
//        }

        val line = lines.first().replaceAll("\t", " ").replaceAll("  ", " ").trim().split(" ")
//        if (line.size >= 2) {
        line[1]
//        } else {
//            null
//        }
    }

}

class GetGatewayIpAddressAndroid(private val context: Context, val isConnectedToWifi: IsConnectedToWifi) : GetGatewayIpAddress {
    override suspend fun invoke(): String? {
        if (!isConnectedToWifi()) return null
        val wifiMgr = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ret =  Formatter.formatIpAddress(wifiMgr.dhcpInfo.gateway)
        return ret
    }

}

class NetworkScannerImpl(val deviceManagerBuilder: DeviceManagerBuilder) : NetworkScanner {

    override suspend fun scan(gatewayIpAddress: String): List<SonoffDeviceId> {
        val base = gatewayIpAddress.let { it.substring(0, it.lastIndexOf('.') + 1) }

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

    private fun CoroutineScope.testIp(ip: String): Deferred<Pair<SonoffDeviceId, Boolean>?> {
        val id = ip.asSonoffDevice()
        return async {
            withTimeoutOrNull(1000L) {
                id to deviceManagerBuilder(id).isReachable()
            }
        }
    }

}

fun main() {
    runBlocking<Unit> {
        startKoin {
            modules(listOf(
                    sonoffModule, sonoffMacOsModule
            ))
        }
        val getConnectedSonoff by GlobalContext.get().koin.inject<GetConnectedSonoff>()
        getConnectedSonoff()?.let {
            it.toggle()
            delay(1000L)
            it.toggle()
        }
        exitProcess(0)
    }
}


interface GetConnectedSonoff {
    suspend operator fun invoke(): SonoffDeviceManager?
}

class GetConnectedSonoffImpl(
        private val networkScanner: NetworkScanner,
        private val deviceManagerBuilder: DeviceManagerBuilder,
        private val getGatewayIpAddress: GetGatewayIpAddress
) : GetConnectedSonoff {


    override suspend fun invoke(): SonoffDeviceManager? = kotlin.runCatching {
        val id = networkScanner.scan(getGatewayIpAddress()!!).first()
        deviceManagerBuilder(id)
    }.getOrNull()


}
