package io.aiico.memora

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteEditorPresenter(private val noteId: String?) {

    private var job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)
    private var view: EditNoteView? = null

    fun onAttach(view: EditNoteView, firstAttach: Boolean) {
        this.view = view
        if (firstAttach && noteId != null) {
            scope.launch {
                val note = withContext(Dispatchers.IO) {
                    NotesRepository
                        .getNote(noteId)
                        .firstOrNull()
                }

                note?.let { view.showNote(note) }
            }
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

        scope.launch {
            withContext(Dispatchers.IO) {
                when (noteId) {
                    null -> NotesRepository.insert(note)
                    else -> NotesRepository.update(note)
                }
                NotesRepository.update(note)
            }
            view?.close()
        }
    }
}
