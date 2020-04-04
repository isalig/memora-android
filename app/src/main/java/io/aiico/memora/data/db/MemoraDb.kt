package io.aiico.memora.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.aiico.memora.domain.Note

@Database(entities = [Note::class], version = 1)
abstract class MemoraDb : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {

        private const val DB_NAME = "MemoraDb"

        fun createInstance(context: Context) = Room
            .databaseBuilder(
                context,
                MemoraDb::class.java,
                DB_NAME
            )
            .build()
    }
}