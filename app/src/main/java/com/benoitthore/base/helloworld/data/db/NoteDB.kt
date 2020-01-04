package com.benoitthore.base.helloworld.data.db

import androidx.room.*
import com.benoitthore.base.lib.repo.Mapper
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
    suspend fun getNotes(): List<NoteDB>
}




// MODEL
data class NoteModel(val text: String, val date: Long)

object Mappers {
    val dtoToModel = object : Mapper<NoteDB, NoteModel> {
        override fun invoke(o: NoteDB) = NoteModel(text = o.text, date = o.date)
    }

    val modelToDto = object : Mapper<NoteModel, NoteDB> {
        override fun invoke(o: NoteModel) = NoteDB(text = o.text, date = o.date)
    }
}