package com.kerencev.translator.di

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}
