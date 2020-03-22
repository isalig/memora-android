package io.aiico.memora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteViewHolder>() {

    private val notes = ArrayList<Note>()

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        with(LayoutInflater.from(parent.context)) {
            NoteViewHolder(inflate(R.layout.list_item_note, parent, false))
        }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun onViewAttachedToWindow(holder: NoteViewHolder) {
        super.onViewAttachedToWindow(holder)
        with(holder) {
            itemView.setOnClickListener {
                onNoteClick.invoke(notes[adapterPosition])
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: NoteViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    fun submit(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }
}