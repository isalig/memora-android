package io.aiico.memora

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotesListPresenter {

    private var job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)
    var view: NotesListView? = null

    private val notesConsumer = object : NotesRepository.NotesConsumer {

        override fun onNotesListChange(notes: List<Note>) {
            view?.showNotes(notes)
        }
    }

    fun onAttach(view: NotesListView) {
        this.view = view
        scope.launch {
            NotesRepository.subscribe(notesConsumer)
        }

    }

    fun onDetach() {
        view = null
        NotesRepository.unsubscribe()
    }
}