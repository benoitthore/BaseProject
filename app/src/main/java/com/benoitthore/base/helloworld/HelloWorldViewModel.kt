package com.benoitthore.base.helloworld

import androidx.lifecycle.viewModelScope
import com.benoitthore.base.helloworld.HelloWorldViewModel.Event
import com.benoitthore.base.helloworld.HelloWorldViewModel.State
import com.benoitthore.base.helloworld.data.HelloWorldRepo
import com.benoitthore.base.helloworld.data.db.NoteModel
import com.benoitthore.base.lib.mvvm.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HelloWorldViewModel : BaseViewModel<State, Event>(), KoinComponent {

    val repo: HelloWorldRepo by inject()
    override val initialState: State get() = State()

    data class State(val data: List<NoteModel> = emptyList())

    sealed class Event {
        object Close : Event()
    }

    init {
        viewModelScope.launch {
            repo.getNotes().collect { list ->
                emitState { State(data = list) }
            }
        }
    }

    fun onAddClicked(noteText: String) {
        repo.addNote(NoteModel(noteText, System.currentTimeMillis()))
    }

    fun onDeleteClicked(note: NoteModel) {
        repo.deleteNote(note)
    }

}