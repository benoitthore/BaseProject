package com.benoitthore.base.helloworld.data.db

import androidx.room.*
import com.benoitthore.base.lib.data.Mapper
import kotlinx.coroutines.flow.Flow

// https://medium.com/mindorks/room-kotlin-android-architecture-components-71cad5a1bb35

// ROOM
@Entity
data class NoteDB(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val text: String, val date: Long)



@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(Note: NoteDB)

    @Update
    suspend fun updateNote(Note: NoteDB)

    @Delete
    suspend fun deleteNote(Note: NoteDB)

    @Query("SELECT * FROM NoteDB WHERE text == :name")
    suspend fun getNoteByText(name: String): List<NoteDB>

    @Query("SELECT * FROM NoteDB")
    fun getNotes(): Flow<List<NoteDB>>
}




// MODEL
data class NoteModel(val text: String, val date: Long, val id : Int? = null)

object NoteMappers {
    val dtoToModel = object : Mapper<NoteDB, NoteModel> {
        override fun invoke(o: NoteDB) = NoteModel(text = o.text, date = o.date, id = o.id)
    }

    val modelToDto = object : Mapper<NoteModel, NoteDB> {
        override fun invoke(o: NoteModel) = NoteDB(text = o.text, date = o.date, id = o.id)
    }
}