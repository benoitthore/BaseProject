package com.benoitthore.base.helloworld

import androidx.lifecycle.viewModelScope
import com.benoitthore.base.helloworld.HelloWorldViewModel.Event
import com.benoitthore.base.helloworld.HelloWorldViewModel.State
import com.benoitthore.base.helloworld.data.HelloWorldRepo
import com.benoitthore.base.helloworld.data.NoteModel
import com.benoitthore.base.lib.mvvm.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HelloWorldViewModel(
) : BaseViewModel<State, Event>(), KoinComponent {

    val repo: HelloWorldRepo by inject()

    init {
        viewModelScope.launch {
            val channel = repo.openSubscription()

            while (!channel.isClosedForReceive) {
                val data = channel.receive()
                emitState { State(data = data) }
            }
        }
    }

    fun onButtonLongClicked() {
        emitEvent { Event.Close }
    }

    private var i = 0
    fun onButtonClicked() {
        repo.clear()
        emitEvent {
            if (i++ % 2 == 0) {
                Event.Blink1
            } else {
                Event.Blink2
            }
        }
    }

    override val initialState: State
        get() = State()

    data class State(val data: List<NoteModel> = emptyList())

    sealed class Event {
        object Close : Event()
        object Blink1 : Event()
        object Blink2 : Event()
    }
}