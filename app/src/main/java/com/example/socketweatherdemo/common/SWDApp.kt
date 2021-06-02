package com.example.socketweatherdemo.common

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import com.example.socketweatherdemo.BuildConfig
import com.example.socketweatherdemo.allowMainThreadDiskOperations
import com.example.socketweatherdemo.initialiseSingletons
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("unused") // It's used in AndroidManifest.xml.
class SWDApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Timber.plant(Timber.DebugTree())
            StrictMode.setThreadPolicy(ThreadPolicy.Builder().detectAll().penaltyDeath().build())
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedRegistrationObjects()
                    .penaltyDeath()
                    .build()
            )
        }

        // Explicitly initialise these dependencies on the main thread as they're
        // needed for the whole app to do its thing.
        allowMainThreadDiskOperations {
            AndroidThreeTen.init(this)
            initialiseSingletons()
        }
    }
}
