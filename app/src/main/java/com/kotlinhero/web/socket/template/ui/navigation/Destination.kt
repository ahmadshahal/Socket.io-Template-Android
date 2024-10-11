package com.kotlinhero.web.socket.template.ui.navigation

/**
 * A sealed class representing different navigation destinations within the application.
 *
 * Each destination is modeled as a distinct object with a specific route, which is used to navigate
 * to that screen. This approach makes navigation type-safe and ensures that only valid routes are used.
 *
 * @property route The route or path used for navigation to the specific screen.
 */
sealed class Destination(val route: String) {

    /**
     * Represents the home screen of the application.
     * This destination is associated with the route "/home_screen" and is used to navigate to the main or
     * starting screen of the app.
     */
    data object HomeScreen : Destination(route = "/home_screen")

    /**
     * Represents the temperature screen of the application.
     * This destination is associated with the route "/temperature_screen" and is used to navigate to the screen
     * displaying temperature-related information.
     */
    data object TemperatureScreen : Destination(route = "/temperature_screen")
}
