package io.aiico.memora.presentation

import io.aiico.memora.domain.Note

interface NotesListView {

    fun showNotes(notes: List<Note>)
}