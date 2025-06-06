package dev.matsem.spacenews

import android.app.Application
import android.util.Log
import dev.matsem.spacenews.shared.SpaceNewsApp
import org.koin.android.ext.koin.androidContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("App", "onCreate")
        SpaceNewsApp.initializeSharedFramework {
            androidContext(this@App)
        }
    }
}
