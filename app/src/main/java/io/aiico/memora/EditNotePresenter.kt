package io.aiico.memora

class EditNotePresenter(private val noteId: String?) {

    private var view: EditNoteView? = null

    fun onAttach(view: EditNoteView, firstAttach: Boolean) {
        this.view = view
        if (firstAttach && noteId != null) {
            NotesRepository.getNote(noteId)?.let { note -> view.showNote(note) }
        }
    }

    fun onDetach() {
        view = null
    }

    fun onSaveButtonClick(title: String, content: String) {
        val note = when (noteId) {
            null -> Note(title = title, content = content)
            else -> Note(noteId, title, content)
        }
        NotesRepository.insertOrUpdate(note)
        view?.close()
    }
}
