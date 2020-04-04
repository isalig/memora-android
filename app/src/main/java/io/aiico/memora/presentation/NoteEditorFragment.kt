package io.aiico.memora.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import io.aiico.memora.R
import io.aiico.memora.domain.Note

class NoteEditorFragment : Fragment(), EditNoteView {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var presenter: NoteEditorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = NoteEditorPresenter(getNoteId())
    }

    private fun getNoteId(): String? = arguments?.getString(KEY_NOTE_ID_ARG)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_note_editor, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        saveButton.setOnClickListener {
            val title = titleEditText.text.trim().toString()
            val content = contentEditText.text.trim().toString()
            presenter.onSaveButtonClick(title, content)
        }
        presenter.onAttach(this, savedInstanceState == null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }

    private fun initViews() {
        with(requireView()) {
            titleEditText = findViewById(R.id.title_edit_text)
            contentEditText = findViewById(R.id.content_edit_text)
            saveButton = findViewById(R.id.save_button)
        }
    }

    override fun showNote(note: Note) {
        titleEditText.setText(note.title)
        contentEditText.setText(note.content)
    }

    override fun close() {
        activity?.onBackPressed()
    }

    companion object {

        private const val KEY_NOTE_ID_ARG = "note_id"

        fun newInstance(noteId: String?) = NoteEditorFragment().apply { arguments =
            prepareArgs(noteId)
        }

        private fun prepareArgs(noteId: String?) = Bundle().apply { putString(KEY_NOTE_ID_ARG, noteId) }
    }
}