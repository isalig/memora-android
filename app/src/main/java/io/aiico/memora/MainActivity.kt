package io.aiico.memora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), NotesListView {

    private val presenter = NotesListPresenter()
    private val adapter = NotesAdapter { note ->
        Toast
            .makeText(this, "Note ${note.title} clicked", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNotesRecyclerView()
    }

    private fun initNotesRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.notes_recycler_view)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
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
}
