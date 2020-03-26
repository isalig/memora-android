package io.aiico.memora

import android.app.Application

@Suppress("unused")
class MemoraApp : Application() {

    override fun onCreate() {
        super.onCreate()
        with(MemoraDb.createInstance(this)) {
            NotesRepository.init(this)
        }
    }
}