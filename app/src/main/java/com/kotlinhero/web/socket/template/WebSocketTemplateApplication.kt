package com.kotlinhero.web.socket.template

import android.app.Application
import com.kotlinhero.web.socket.template.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 * The application class for the WebSocketTemplate app.
 *
 * This class serves as the base application context and is responsible for initializing
 * dependency injection using Koin when the application is created.
 */
class WebSocketTemplateApplication : Application() {

    /**
     * Called when the application is starting, before any activities or other application
     * components have been created. This method is used to perform one-time initialization
     * tasks such as setting up dependency injection.
     */
    override fun onCreate() {
        super.onCreate()

        // Start Koin for dependency injection
        startKoin {
            // Use Android logger for Koin logging
            androidLogger()
            // Provide the Android context for Koin
            androidContext(this@WebSocketTemplateApplication)
            // Load application modules for dependency injection
            modules(AppModule)
        }
    }
}