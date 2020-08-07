package com.benoitthore.sonoff.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.benoitthore.base.R
import com.benoitthore.base.lib.mvvm.BaseViewModel
import com.benoitthore.sonoff.data.SonoffLocalRepo
import com.benoitthore.sonoff.data.SonoffRepo
import com.benoitthore.sonoff.data.SonoffResponse
import com.benoitthore.sonoff.data.asSonoffDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SOnOffFragment : Fragment(R.layout.sonoff) {

    private val vm = SonoffViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.on_off_button)

        button.setOnClickListener {
            vm.switch()
        }

        vm.observe(this) {
            stateObserver { newState ->
                when (newState) {
                    is SonoffViewModel.State.Success -> {
                        button.text = if (newState.isOn) "ON" else "OFF"
                    }
                    SonoffViewModel.State.Loading -> {
                        button.text = "..."
                    }
                    SonoffViewModel.State.Error -> button.text = "Error"
                }

            }
        }
    }


}

class SonoffViewModel : BaseViewModel<SonoffViewModel.State, SonoffViewModel.Event>() {

    sealed class State {
        class Success(val isOn: Boolean) : State()
        object Loading : State()
        object Error : State()
    }

    object Event

    private val repo: SonoffRepo = SonoffLocalRepo()
    private val device = "192.168.1.144".asSonoffDevice()
    private var job: Job? = null

    init {
        emitState { State.Loading }
        job = viewModelScope.launch {
            emit(repo.getState(device))
        }
    }

    private fun emit(apiResponse: SonoffResponse<Boolean>) {
        when (apiResponse) {
            is SonoffResponse.Success -> emitState { State.Success(apiResponse.value) }
            is SonoffResponse.Error -> emitState { State.Error }
        }
    }

    fun switch() {
        if (job?.isActive == true) {
            return
        }
        emitState { State.Loading }
        job = GlobalScope.launch(Dispatchers.IO) {
            emit(repo.switch(device))
        }
    }

    override val initialState: State get() = State.Loading

}