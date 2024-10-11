package com.kotlinhero.web.socket.template.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlinhero.web.socket.template.domain.states.SocketConnection
import com.kotlinhero.web.socket.template.ui.screens.HomeScreen
import com.kotlinhero.web.socket.template.ui.screens.TemperatureScreen
import com.kotlinhero.web.socket.template.ui.viewmodels.TemperatureViewModel
import org.koin.androidx.compose.koinViewModel


/**
 * A composable function that sets up the navigation graph for the application.
 *
 * This function defines the navigation structure using the Jetpack Navigation component,
 * allowing users to navigate between different screens (destinations) within the app.
 *
 * @param navController The [NavHostController] used to manage navigation within the app.
 * @param startDestination The route of the initial screen to be displayed when the app starts.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        // Define a composable route for the TemperatureScreen
        composable(route = Destination.TemperatureScreen.route) {
            // Get the ViewModel instance for temperature management using Koin
            val viewModel: TemperatureViewModel = koinViewModel()
            // Collect the current temperature from the ViewModel's temperatureFlow
            val temperature by viewModel.temperatureFlow.collectAsStateWithLifecycle(
                initialValue = null
            )
            // Collect the current socket connection state from the ViewModel
            val socketConnectionState by viewModel.connectionFlow.collectAsStateWithLifecycle(
                initialValue = SocketConnection.Disconnected  // Default initial connection state
            )
            // Display the TemperatureScreen with the collected temperature and socket connection state
            TemperatureScreen(
                temperature = temperature,
                socketConnection = socketConnectionState,
                navController = navController
            )
        }

        // Define a composable route for the HomeScreen
        composable(route = Destination.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}