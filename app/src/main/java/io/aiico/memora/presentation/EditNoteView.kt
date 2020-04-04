package io.aiico.memora.presentation

import io.aiico.memora.domain.Note

interface EditNoteView {

    fun showNote(note: Note)
    fun close()
}
