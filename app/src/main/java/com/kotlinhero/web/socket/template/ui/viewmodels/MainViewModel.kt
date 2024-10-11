package com.kotlinhero.web.socket.template.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kotlinhero.web.socket.template.data.SocketService

/**
 * A ViewModel class that manages the application's state including socket connection and lifecycle.
 *
 * This class is responsible for establishing and terminating the connection with the
 * SocketService. It initializes the connection when the ViewModel is created and ensures
 * that the connection is properly closed when the ViewModel is cleared.
 *
 * @param socketService The [SocketService] instance used to manage the WebSocket connection.
 *                     This should be provided via dependency injection or instantiated in the application.
 */
class MainViewModel(
    private val socketService: SocketService
) : ViewModel() {

    /**
     * Initializes the ViewModel and establishes the socket connection.
     * This function is called when the ViewModel is created.
     */
    init {
        connect()
    }

    /**
     * Connects to the WebSocket server using the SocketService.
     * The token required for authentication should be passed here.
     *
     * In a real application, replace "TOKEN_SHOULD_BE_HERE" with an actual token or
     * retrieve it from a secure source.
     */
    private fun connect() = socketService.connect("TOKEN_SHOULD_BE_HERE")

    /**
     * Disconnects from the WebSocket server.
     * This function is called when the ViewModel is cleared to ensure that the
     * connection is terminated properly.
     */
    private fun disconnect() = socketService.disconnect()

    /**
     * Cleans up resources when the ViewModel is cleared.
     * This function is called automatically when the ViewModel is no longer used.
     * It disconnects the socket service to prevent memory leaks and ensure that
     * the connection is terminated properly.
     */
    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}