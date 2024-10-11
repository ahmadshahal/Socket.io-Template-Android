package com.kotlinhero.web.socket.template.domain.entity

import com.kotlinhero.web.socket.template.data.models.TemperatureModel

/**
 * A sealed class that represents events received from a WebSocket connection.
 * Each event type is modeled as a subclass with a specific payload (data) associated with it.
 *
 * This sealed class ensures that all possible event types are explicitly defined, making the code
 * more type-safe and easier to handle when processing incoming events.
 *
 * @param T The type of data associated with the received event.
 * @property data The data payload of the event.
 */
sealed class ReceivedSocketEvent<T>(val data: T) {

    /**
     * Represents an event where temperature data is received from the WebSocket.
     * This class extends the [ReceivedSocketEvent] sealed class and specifically deals with
     * temperature-related data.
     *
     * @param data The temperature data received from the WebSocket, encapsulated in a [TemperatureModel] object.
     */
    class ReceiveTemperatureEvent(data: TemperatureModel) : ReceivedSocketEvent<TemperatureModel>(data)
}