package io.aiico.memora

import android.app.Application
import io.aiico.memora.data.NotesRepository
import io.aiico.memora.data.db.MemoraDb

@Suppress("unused")
class MemoraApp : Application() {

    override fun onCreate() {
        super.onCreate()
        with(MemoraDb.createInstance(this)) {
            NotesRepository.init(this)
        }
    }
}