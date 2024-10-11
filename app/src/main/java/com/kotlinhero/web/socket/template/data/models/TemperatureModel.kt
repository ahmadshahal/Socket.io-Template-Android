package com.kotlinhero.web.socket.template.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A data class representing a temperature measurement.
 *
 * This model is used to encapsulate temperature data, including the value of the temperature
 * and the unit in which it is measured. It provides a structured way to handle temperature-related
 * information within the application.
 *
 * @property value The numeric value of the temperature.
 * @property unit The unit of measurement for the temperature (e.g., Celsius, Fahrenheit).
 */
@Serializable
data class TemperatureModel(
    @SerialName("value")
    val value: Float,

    @SerialName("unit")
    val unit: String,
)