package com.kotlinhero.web.socket.template.domain.states


/**
 * A sealed class representing the various states of a WebSocket connection.
 *
 * This sealed class is used to explicitly define the different connection states
 * that a WebSocket can be in, making the state management more structured and type-safe.
 */
sealed class SocketConnection {

    /**
     * Represents the state where the WebSocket connection is not connected to the server.
     * This state indicates that the socket is fully disconnected and no communication is possible.
     */
    data object Disconnected : SocketConnection()

    /**
     * Represents the state where the WebSocket connection is actively connected to the server.
     * This state indicates that the socket is successfully established, and data can be sent or received.
     */
    data object Connected : SocketConnection()

    /**
     * Represents the state where the WebSocket connection is in the process of being established.
     * This state indicates that the socket is currently attempting to connect to the server.
     */
    data object Connecting : SocketConnection()
}