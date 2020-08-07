package com.benoitthore.sonoff.ui

import androidx.lifecycle.viewModelScope
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.sonoff.data.SonoffLocalRepo
import com.benoitthore.sonoff.data.SonoffRepo
import com.benoitthore.sonoff.data.SonoffResponse
import com.benoitthore.sonoff.data.asSonoffDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SonoffViewModel : BaseViewModel<SonoffViewModel.State, SonoffViewModel.Event>() {

    sealed class State {
        class Success(val isOn: Boolean) : State()
        object Loading : State()
        class Error(val message: String? = null) : State()
    }

    object Event

    private val repo: SonoffRepo = SonoffLocalRepo()
    private val device = "192.168.1.144".asSonoffDevice()
    private var job: Job? = null

    init {
        emitState { State.Loading }
        job =viewModelScope.launch(Dispatchers.IO) {
            emit(repo.getState(device))
        }
    }

    private fun emit(apiResponse: SonoffResponse<Boolean>) {
        val state = when (apiResponse) {
            is SonoffResponse.Success -> State.Success(apiResponse.value)
            is SonoffResponse.Error -> State.Error(apiResponse.exception.toString())
        }
        emitState { state }
    }

    fun switch() {
        if (job?.isActive == true) {
            return
        }
        emitState { State.Loading }
        job = viewModelScope.launch(Dispatchers.IO) {
            emit(repo.switch(device))
        }
    }

    override val initialState: State get() = State.Loading

}