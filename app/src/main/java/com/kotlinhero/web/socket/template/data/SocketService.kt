package com.kotlinhero.web.socket.template.data

import android.util.Log
import com.kotlinhero.web.socket.template.data.models.TemperatureModel
import com.kotlinhero.web.socket.template.domain.entity.ReceivedSocketEvent
import com.kotlinhero.web.socket.template.domain.enums.SocketIOEvent
import com.kotlinhero.web.socket.template.domain.states.SocketConnection
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * SocketService is responsible for managing the WebSocket connection and handling communication
 * with the server. It provides methods to connect, disconnect, send data, and listen for incoming events.
 *
 * @property json The JSON serializer used to encode and decode data for communication.
 */
class SocketService(private val json: Json) {
    // Represents the current state of the socket connection as a flow.
    private val _connectionFlow =
        MutableStateFlow<SocketConnection>(SocketConnection.Disconnected)
    val connectionFlow = _connectionFlow.asStateFlow()

    // Holds the events received from the socket connection as a flow.
    private val _receivedEventsFlow =
        MutableSharedFlow<ReceivedSocketEvent<*>>(extraBufferCapacity = 10)
    val receivedEventsFlow = _receivedEventsFlow.asSharedFlow()

    // Holds the reference to the actual Socket.IO instance.
    private var socket: Socket? = null

    /**
     * Initializes the socket instance with the required headers and configuration.
     * This method disconnects any existing connection before setting up a new one.
     *
     * @param token The authorization token required to authenticate the socket connection.
     */
    private fun initSocket(token: String) {
        socket?.disconnect()  // Disconnect existing socket if any before reconnecting.
        val extraHeaders = mapOf(
            "authorization" to listOf(token),
            "platform" to listOf("ANDROID"),
            "version" to listOf("4")
        )
        val options = IO.Options
            .builder()
            .setExtraHeaders(extraHeaders)
            .build()
        socket = IO.socket("https://Mobile-Dev-Meetup/2024-10", options)
    }

    /**
     * Establishes a connection to the WebSocket server using the specified token.
     * Initializes the socket and sets up event listeners.
     *
     * @param token The authorization token required for establishing the socket connection.
     */
    fun connect(token: String) {
        initSocket(token)
        initListeners()
        Log.d(TAG, "Connecting")
        _connectionFlow.update { SocketConnection.Connecting }
        socket?.connect()
    }

    /**
     * Disconnects the WebSocket connection if it's currently active.
     */
    fun disconnect() = socket?.disconnect()

    /**
     * Sends data to the server for a specific socket event using a data model and its serializer.
     *
     * @param T The type of the data to be sent.
     * @param socketIOEvent The event for which the data is being sent.
     * @param data The data object to be sent with the event.
     * @param kSerializer The serializer used to encode the data object into a JSON string.
     */
    fun <T> send(socketIOEvent: SocketIOEvent, data: T, kSerializer: KSerializer<T>) {
        val encodedData = json.encodeToString(kSerializer, data)
        socket?.emit(socketIOEvent.key, encodedData)
        Log.d(TAG, "SENT: $socketIOEvent $encodedData")
    }

    /**
     * Sends an event to the server without any additional data.
     *
     * @param socketIOEvent The event to be sent to the server.
     */
    fun send(socketIOEvent: SocketIOEvent) {
        socket?.emit(socketIOEvent.key)
        Log.d(TAG, "SENT: $socketIOEvent")
    }

    /**
     * Sets up listeners for various socket events, such as connection state changes and data reception.
     * Updates the connection state flow and emits received events into the shared flow.
     */
    private fun initListeners() {
        // Listen for the connection event and update the connection flow state.
        socket?.on(SocketIOEvent.CONNECT.key) {
            Log.d(TAG, "CONNECTED")
            _connectionFlow.update { SocketConnection.Connected }
        }

        // Listen for the disconnection event and update the connection flow state.
        socket?.on(SocketIOEvent.DISCONNECT.key) {
            Log.d(TAG, "DISCONNECTED")
            _connectionFlow.update { SocketConnection.Disconnected }
        }

        // Listen for the connect error event and update the connection flow state.
        socket?.on(SocketIOEvent.CONNECT_ERROR.key) {
            Log.d(TAG, "CONNECT_ERROR")
            _connectionFlow.update { SocketConnection.Disconnected }
        }

        // Listen for the reconnect event and update the connection flow state.
        socket?.on(SocketIOEvent.RECONNECTING.key) {
            Log.d(TAG, "CONNECT_ERROR")
            _connectionFlow.update { SocketConnection.Connecting }
        }

        // Listen for temperature data received from the server and emit the event into the shared flow.
        socket?.on(SocketIOEvent.RECEIVE_TEMPERATURE.key) { received ->
            val data = received[0].toString()
            Log.d(TAG, "RECEIVE_TEMPERATURE: $data")
            val temperatureModel = json.decodeFromString<TemperatureModel>(data)
            val temperatureEvent = ReceivedSocketEvent.ReceiveTemperatureEvent(temperatureModel)
            _receivedEventsFlow.tryEmit(temperatureEvent)
        }
    }

    companion object {
        const val TAG = "SocketService"
    }
}