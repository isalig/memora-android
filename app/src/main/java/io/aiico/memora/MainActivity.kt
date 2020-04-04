package io.aiico.memora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.aiico.memora.presentation.NotesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: showListFragment()
    }

    private fun showListFragment() {
        with(supportFragmentManager.beginTransaction()) {
            replace(android.R.id.content, NotesListFragment.newInstance())
            commit()
        }
    }
}
