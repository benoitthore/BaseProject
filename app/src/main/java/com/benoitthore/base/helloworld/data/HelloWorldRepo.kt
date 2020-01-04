package com.benoitthore.base.helloworld.data

import com.benoitthore.base.helloworld.data.db.*
import com.benoitthore.base.lib.repo.Mapper
import com.benoitthore.base.lib.repo.invoke
import com.benoitthore.enamel.core.print
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HelloWorldRepo : KoinComponent {
    private val dtoToModel: Mapper<NoteDB, NoteModel> = Mappers.dtoToModel
    private val modelToDTO: Mapper<NoteModel, NoteDB> = Mappers.modelToDto


    private val noteDAO : NoteDao by inject()

    init {
        GlobalScope.launch {

            while(true){
                delay(1000L)
                noteDAO.insertNote(NoteDB(text = "text",date = System.currentTimeMillis()))
            }

        }

        GlobalScope.launch {
//            noteDAO.getNotes().collect { data->
//                println("ben_\t$data")
//            }
        }
    }
    // TODO Replace with Room
    private val _notes = mutableListOf<NoteDB>()
    private val notesModel get() = dtoToModel.invoke(_notes)

    private val channel = ConflatedBroadcastChannel(notesModel)

    fun openSubscription() = channel.openSubscription()

    fun addNote(note: NoteModel) {
        _notes += modelToDTO(note)
        broadcast()
    }

    fun broadcast() {
        GlobalScope.launch { channel.send(notesModel) }
    }

    fun clear() {
        _notes.clear()
        broadcast()
    }
}