package io.aiico.memora

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val titleTextView: TextView = view.findViewById(R.id.title_text_view)
    private val contentTextView: TextView = view.findViewById(R.id.content_text_view)

    fun bind(note: Note) {
        titleTextView.text = note.title
        contentTextView.text = note.content
    }
}