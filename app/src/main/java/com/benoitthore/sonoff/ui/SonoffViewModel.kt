package com.benoitthore.sonoff.ui

import androidx.lifecycle.viewModelScope
import com.benoitthore.base.lib.data.ApiResponse
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.sonoff.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SonoffViewModel : BaseViewModel<SonoffViewModel.State, SonoffViewModel.Event>() {

    sealed class State {
        class Success(val isOn: Boolean) : State()
        object Loading : State()
        class Error(val message: String? = null) : State()
    }

    object Event

    // TODO Inject
    private val repo: SonoffRepository = SonoffRepositoryImpl()
    private val device = "192.168.1.144".asSonoffDevice()

    private suspend inline fun getDeviceManagerOrEmitError(block: (SonoffDeviceManager) -> Unit) {
        repo.getDeviceManager(device)?.let(block)
                ?: emitState { State.Error("Device not found $device") }
    }

    private var job: Job? = null

    init {
        emitState { State.Loading }
        job = viewModelScope.launch(Dispatchers.IO) {
            getDeviceManagerOrEmitError { deviceManager ->
                emit(deviceManager.getState())
            }
        }
    }

    private fun emit(apiResponse: ApiResponse<PowerData>) {
        val state = when (apiResponse) {
            is ApiResponse.Success -> State.Success(apiResponse.value.isOn)
            else -> State.Error()
        }
        emitState { state }
    }

    fun switch() {
        if (job?.isActive == true) {
            return
        }
        emitState { State.Loading }
        job = viewModelScope.launch(Dispatchers.IO) {
            getDeviceManagerOrEmitError { deviceManager ->
                emit(deviceManager.toggle())

            }
        }
    }

    override val initialState: State get() = State.Loading

}