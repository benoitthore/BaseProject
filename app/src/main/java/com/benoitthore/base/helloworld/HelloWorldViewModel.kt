package com.benoitthore.base.helloworld

import com.benoitthore.base.mvvm.BaseViewModel
import  com.benoitthore.base.helloworld.HelloWorldViewModel.*

class HelloWorldViewModel : BaseViewModel<State, Event>() {

    override val initialState: State
        get() = State()

    data class State(val data: List<NoteModel> = emptyList())

    sealed class Event {
        object Blink1 : Event()
        object Blink2 : Event()
    }
}

data class NoteModel(val data: String, val date: Long)