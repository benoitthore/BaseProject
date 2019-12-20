package com.benoitthore.base.helloworld.data

import com.benoitthore.base.lib.repo.Mapper
import com.benoitthore.base.lib.repo.invoke
import com.benoitthore.enamel.core.math.random
import com.benoitthore.enamel.core.randomString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class HelloWorldRepo : KoinComponent {
    private val dtoToModel: Mapper<NoteDTO, NoteModel> = Mappers.dtoToModel
    private val modelToDTO: Mapper<NoteModel, NoteDTO> = Mappers.modelToDto

    // TODO Replace with Room
    private val _notes = mutableListOf<NoteDTO>()
    private val notesModel get() = dtoToModel.invoke(_notes)

    private val channel = ConflatedBroadcastChannel(notesModel)

    init {
        fun addRandomNote() {
            val text = randomString(random(5, 25).toInt(), 'A'..'Z', 'a'..'z')
            val note = NoteModel(text, System.currentTimeMillis())
            addNote(note)
        }

        addRandomNote()
        addRandomNote()
        GlobalScope.launch {
            while (true) {
                delay(2000L)
                addRandomNote()
            }
        }
    }


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


data class NoteDTO(val text: String, val date: Long)
data class NoteModel(val text: String, val date: Long)

object Mappers {
    val dtoToModel = object : Mapper<NoteDTO, NoteModel> {
        override fun invoke(o: NoteDTO) = NoteModel(text = o.text, date = o.date)
    }

    val modelToDto = object : Mapper<NoteModel, NoteDTO> {
        override fun invoke(o: NoteModel) = NoteDTO(text = o.text, date = o.date)
    }
}