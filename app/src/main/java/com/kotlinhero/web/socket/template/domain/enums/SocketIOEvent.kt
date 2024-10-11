package com.kotlinhero.web.socket.template.domain.enums

/**
 * An enum class representing different types of Socket.IO events that can be emitted or received.
 *
 * Each enum constant is associated with a specific event key that corresponds to the event name
 * used in the Socket.IO communication protocol. This makes it easier to manage and reference
 * event names throughout the codebase in a type-safe manner.
 *
 * @property key The string representation of the event name used by Socket.IO.
 */
enum class SocketIOEvent(val key: String) {

    /**
     * Represents the event when a WebSocket connection is successfully established.
     * This event is triggered when the client connects to the server.
     */
    CONNECT("connect"),

    /**
     * Represents the event when the WebSocket connection is closed or terminated.
     * This event is triggered when the client disconnects from the server.
     */
    DISCONNECT("disconnect"),

    /**
     * Represents an error event that occurs when a socket connection fails.
     * This event is triggered when the Socket.IO client is unable to
     * connect to the server.
     */
    CONNECT_ERROR("connect_error"),

    /**
     * Represents the event used to subscribe to a specific channel or topic.
     * This is typically used to start receiving updates or data from the server for that channel.
     */
    SUBSCRIBE("SUBSCRIBE"),

    /**
     * Represents the event used to unsubscribe from a specific channel or topic.
     * This stops the client from receiving updates or data from the server for that channel.
     */
    UNSUBSCRIBE("UNSUBSCRIBE"),

    /**
     * Represents the event when temperature data is received from the server.
     * This event is used to handle incoming temperature-related information in real-time.
     */
    RECEIVE_TEMPERATURE("RECEIVE_TEMPERATURE"),
}