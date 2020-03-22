package io.aiico.memora

class NotesListPresenter {

    var view: NotesListView? = null

    private val notesConsumer = object : NotesRepository.NotesConsumer {

        override fun onNotesListChange(notes: List<Note>) {
            view?.showNotes(notes)
        }
    }

    fun onAttach(view: NotesListView) {
        this.view = view
        NotesRepository.subscribe(notesConsumer)
    }

    fun onDetach() {
        view = null
        NotesRepository.unsubscribe()
    }
}