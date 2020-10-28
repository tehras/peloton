package com.github.tehras.peloton.app

import android.app.Application
import com.github.tehras.data.di.overviewModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PelotonApp : Application() {
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        // start koin.
        startKoin {
            // Android context.
            androidContext(this@PelotonApp)

            // Init modules.
            modules(viewModelsModule, overviewModule)
        }
    }
}