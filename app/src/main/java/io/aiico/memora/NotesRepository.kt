package io.aiico.memora

object NotesRepository {

    private val notes = ArrayList<Note>()
    private var consumer: NotesConsumer? = null

    fun insertOrUpdate(sourceNote: Note) {
        val collisionIndex = notes.indexOfFirst { note -> note.id == sourceNote.id }
        if (collisionIndex != -1) {
            notes[collisionIndex] = sourceNote
        } else {
            notes.add(sourceNote)
        }
    }

    fun getNote(noteId: String) = notes.firstOrNull { note -> note.id == noteId }

    fun subscribe(consumer: NotesConsumer) {
        this.consumer = consumer
        this.consumer?.onNotesListChange(notes)
    }

    fun unsubscribe() {
        consumer = null
    }

    interface NotesConsumer {

        fun onNotesListChange(notes: List<Note>)
    }
}