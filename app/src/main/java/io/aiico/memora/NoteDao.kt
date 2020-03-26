package io.aiico.memora

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Query("SELECT id, title, content FROM notes")
    fun list(): List<Note>

    @Query("SELECT id, title, content FROM notes WHERE id IS :id")
    fun findById(id: String): Array<Note>
}