package com.benoitthore.base.helloworld.data

import com.benoitthore.base.MyKoinModule
import com.benoitthore.base.helloworld.data.db.*
import com.benoitthore.base.lib.repo.Mapper
import com.benoitthore.base.lib.repo.invoke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HelloWorldRepo : KoinComponent {
    private val dtoToModel: Mapper<NoteDB, NoteModel> by inject(MyKoinModule.Keys.noteDtoToModel)
    private val modelToDTO: Mapper<NoteModel, NoteDB> by inject(MyKoinModule.Keys.noteModelToDto)
    private val noteDAO: NoteDao by inject()

    /**
    We don't want to use the ViewModel scope when accessing the Database:
    if the activity gets killed we still want to finish the DB transaction
     */
    private val daoScope: CoroutineScope by inject(MyKoinModule.Keys.globalScope)

    fun getNotes() = noteDAO.getNotes().map { dtoToModel(it) }

    fun addNote(note: NoteModel) {
        daoScope.launch { noteDAO.insertNote(modelToDTO(note)) }
    }

    fun deleteNote(note: NoteModel) {
        daoScope.launch {
            noteDAO.deleteNote(modelToDTO(note))
        }
    }

}