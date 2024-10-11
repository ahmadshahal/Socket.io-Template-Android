package com.kotlinhero.web.socket.template.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kotlinhero.web.socket.template.data.SocketService
import com.kotlinhero.web.socket.template.domain.entity.ReceivedSocketEvent
import com.kotlinhero.web.socket.template.domain.enums.SocketIOEvent
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

/**
 * A ViewModel class that manages temperature data received from a WebSocket connection.
 *
 * This class interacts with the [SocketService] to receive temperature updates and
 * manage the subscription to temperature events. It exposes the temperature data and
 * connection status as flows for UI consumption.
 *
 * @param socketService The [SocketService] instance used to manage the WebSocket connection
 *                      and receive socket events. This should be provided via dependency injection.
 */
class TemperatureViewModel(
    private val socketService: SocketService
) : ViewModel() {

    /**
     * A flow that emits temperature data received from the socket.
     * It filters incoming events to only those of type [ReceivedSocketEvent.ReceiveTemperatureEvent]
     * and maps the data to expose the temperature model directly.
     */
    val temperatureFlow = socketService.receivedEventsFlow
        .filterIsInstance<ReceivedSocketEvent.ReceiveTemperatureEvent>()  // Filter for temperature events
        .map { it.data }  // Map to extract the temperature data

    /**
     * A flow that emits the current connection state of the socket.
     * This flow is used to observe the connection status and update the UI accordingly.
     */
    val connectionFlow = socketService.connectionFlow

    /**
     * Initializes the ViewModel and subscribes to temperature updates.
     * This function is called when the ViewModel is created to start receiving
     * temperature data from the WebSocket.
     */
    init {
        subscribe()
    }

    /**
     * Sends a subscription request to the WebSocket server to start receiving temperature updates.
     */
    private fun subscribe() = socketService.send(SocketIOEvent.SUBSCRIBE)

    /**
     * Sends an unsubscribe request to the WebSocket server to stop receiving temperature updates.
     * This function is called when the ViewModel is cleared.
     */
    private fun unsubscribe() = socketService.send(SocketIOEvent.UNSUBSCRIBE)

    /**
     * Cleans up resources when the ViewModel is cleared.
     * This function is called automatically when the ViewModel is no longer used.
     * It unsubscribes from temperature updates to prevent memory leaks and ensure that
     * the connection is properly managed.
     */
    override fun onCleared() {
        super.onCleared()
        unsubscribe()
    }
}