package io.aiico.memora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesListFragment : Fragment(), NotesListView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_notes_list, container, false)

    private val presenter = NotesListPresenter()
    private val adapter = NotesAdapter { note ->
        showNoteEditorFragment(note.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotesRecyclerView()
        view
            .findViewById<FloatingActionButton>(R.id.create_note_fab)
            .setOnClickListener {
                showNoteEditorFragment()
            }
    }

    private fun showNoteEditorFragment(noteId: String? = null) {
        with(requireFragmentManager().beginTransaction()) {
            replace(android.R.id.content, NoteEditorFragment.newInstance(noteId))
            addToBackStack(null)
            commit()
        }
    }

    private fun initNotesRecyclerView() {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.notes_recycler_view)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetach()
    }

    override fun showNotes(notes: List<Note>) {
        adapter.submit(notes)
    }

    companion object {

        fun newInstance() = NotesListFragment()
    }
}