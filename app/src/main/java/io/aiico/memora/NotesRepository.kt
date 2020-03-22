package io.aiico.memora

object NotesRepository {

    private val notes = ArrayList<Note>()
    private var consumer: NotesConsumer? = null

    init {
        createNote("Мой первый записка", "Это мой первый очень интересный запика, где я буду писать всякий интересный мысль")
        createNote("Мой второй записка", "Это мой второй очень интересный запика, где я буду писать всякий интересный мысль")
        createNote("Мой третий записка", "Это мой третий очень интересный запика, где я буду писать всякий интересный мысль")
    }

    fun createNote(title: String, content: String) {
        notes.add(Note(title = title, content = content))
        consumer?.onNotesListChange(notes)
    }

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