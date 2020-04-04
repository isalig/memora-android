package io.aiico.memora.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.aiico.memora.R
import io.aiico.memora.domain.Note

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val titleTextView: TextView = view.findViewById(R.id.title_edit_text)
    private val contentTextView: TextView = view.findViewById(R.id.content_edit_text)

    fun bind(note: Note) {
        titleTextView.text = note.title
        contentTextView.text = note.content
    }
}