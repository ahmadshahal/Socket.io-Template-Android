package com.kotlinhero.web.socket.template.di

import com.kotlinhero.web.socket.template.data.SocketService
import com.kotlinhero.web.socket.template.ui.viewmodels.MainViewModel
import com.kotlinhero.web.socket.template.ui.viewmodels.TemperatureViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the WebSocketTemplate application.
 *
 * This module is responsible for providing dependencies required throughout the application.
 * It defines how instances of various components are created and their lifecycle
 * (e.g., singletons or factories).
 */
val AppModule = module {
    /**
     * Provides a configured instance of [Json] for JSON serialization and deserialization.
     *
     * The Json instance is created as a factory, meaning a new instance will be created
     * each time it is requested. The configuration options include:
     * - [prettyPrint]: Enables pretty printing of JSON output for better readability.
     * - [isLenient]: Allows lenient parsing, meaning it can handle some malformed JSON.
     * - [ignoreUnknownKeys]: Ignores any keys that are not defined in the data class when deserializing.
     */
    factory {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    /**
     * Provides a singleton instance of [SocketService].
     *
     * The SocketService instance is created once and reused throughout the application.
     * It is initialized with the required dependencies, such as the [Json] instance.
     */
    single { SocketService(get()) }

    /**
     * Provides a ViewModel for the main activity.
     *
     * The MainViewModel is created using the socket service dependency and will be
     * scoped to the lifecycle of the activity or fragment that requests it.
     */
    viewModel { MainViewModel(get()) }

    /**
     * Provides a ViewModel for the temperature screen.
     *
     * The TemperatureViewModel is created using the socket service dependency and will
     * be scoped to the lifecycle of the navigation destination that requests it.
     */
    viewModel { TemperatureViewModel(get()) }
}