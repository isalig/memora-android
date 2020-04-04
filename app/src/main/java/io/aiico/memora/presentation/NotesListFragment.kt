package io.aiico.memora.presentation

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import io.aiico.memora.R
import io.aiico.memora.domain.Note
import io.aiico.memora.presentation.adapter.NotesAdapter

class NotesListFragment : Fragment(), NotesListView {

    private var layoutManagerType =
        LayoutManagerType.LINEAR
    private val bottomAppBarShapeDrawable = MaterialShapeDrawable()

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var createNoteFab: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView

    private var cornerRadius: Float = 0f

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cornerRadius = context.resources.getDimension(R.dimen.corner_radius_default)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_notes_list, container, false)

    private val presenter = NotesListPresenter()
    private val adapter = NotesAdapter { note ->
        showNoteEditorFragment(note.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomAppBar = view.findViewById(R.id.bottom_bar)
        createNoteFab = view.findViewById(R.id.create_note_fab)
        notesRecyclerView = requireView().findViewById(R.id.notes_recycler_view)
        initNotesRecyclerView()
        createNoteFab.setOnClickListener {
            showNoteEditorFragment()
        }
        prepareAppBarDrawable()
        val shapeAppearanceModel = ShapeAppearanceModel
            .builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(cornerRadius)
            .build()
        createNoteFab.shapeAppearanceModel = shapeAppearanceModel
    }

    private fun prepareAppBarDrawable() {
        val shapeAppearanceModel = ShapeAppearanceModel
            .builder()
            .setTopLeftCornerSize(cornerRadius)
            .setTopRightCornerSize(cornerRadius)
            .build()
        bottomAppBarShapeDrawable.shapeAppearanceModel = shapeAppearanceModel
        bottomAppBarShapeDrawable.shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
        bottomAppBarShapeDrawable.paintStyle = Paint.Style.FILL
        bottomAppBarShapeDrawable.elevation = cornerRadius
        bottomAppBarShapeDrawable.initializeElevationOverlay(context)
        DrawableCompat.setTintList(bottomAppBarShapeDrawable, bottomAppBar.backgroundTint)
        ViewCompat.setBackground(bottomAppBar, bottomAppBarShapeDrawable)
        bottomAppBar.setNavigationOnClickListener {
            layoutManagerType = layoutManagerType.getOppositeType()
            updateLayoutManagerIcon()
            updateLayoutManger()
        }
    }

    private fun showNoteEditorFragment(noteId: String? = null) {
        with(requireFragmentManager().beginTransaction()) {
            replace(android.R.id.content,
                NoteEditorFragment.newInstance(noteId)
            )
            addToBackStack(null)
            commit()
        }
    }

    private fun initNotesRecyclerView() {
        updateLayoutManger()
        updateLayoutManagerIcon()
        notesRecyclerView.adapter = adapter
    }

    private fun updateLayoutManagerIcon() {
        bottomAppBar.setNavigationIcon(layoutManagerType.getIconResId())
    }

    private fun updateLayoutManger() {
        notesRecyclerView.layoutManager = layoutManagerType.createLayoutManger(notesRecyclerView.context)
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

    private enum class LayoutManagerType {

        LINEAR {
            override fun getIconResId() = R.drawable.ic_linear
            override fun getOppositeType() =
                GRID
            override fun createLayoutManger(context: Context) =
                LinearLayoutManager(context)
        },
        GRID {
            override fun getIconResId() = R.drawable.ic_grid
            override fun getOppositeType() =
                LINEAR
            override fun createLayoutManger(context: Context) =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        };

        abstract fun getIconResId(): Int
        abstract fun getOppositeType(): LayoutManagerType
        abstract fun createLayoutManger(context: Context): RecyclerView.LayoutManager
    }
}