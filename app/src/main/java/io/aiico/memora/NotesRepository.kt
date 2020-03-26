package io.aiico.memora

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NotesRepository {

    private lateinit var dao: NoteDao
    private var consumer: NotesConsumer? = null

    fun init(db: MemoraDb) {
        dao = db.noteDao
    }

    fun update(note: Note) {
        dao.update(note)
    }

    fun insert(note: Note) {
        dao.insert(note)
    }

    fun getNote(id: String) = dao.findById(id)

    suspend fun subscribe(consumer: NotesConsumer) {
        this.consumer = consumer
        val notes = withContext(Dispatchers.IO) {
            dao.list()
        }
        this.consumer?.onNotesListChange(notes)
    }

    fun unsubscribe() {
        consumer = null
    }

    interface NotesConsumer {

        fun onNotesListChange(notes: List<Note>)
    }
}