package com.fneis.myevents.application

import android.app.Application
import android.content.Context
import com.fneis.myevents.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MyEventsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyEventsApp)
            modules(allModules)
        }
    }

    companion object {
        var context: Context? = null
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}
