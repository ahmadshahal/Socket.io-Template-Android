package com.kotlinhero.web.socket.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.kotlinhero.web.socket.template.ui.navigation.Destination
import com.kotlinhero.web.socket.template.ui.navigation.NavGraph
import com.kotlinhero.web.socket.template.ui.theme.WebSocketTemplateTheme
import com.kotlinhero.web.socket.template.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * The main activity of the application, serving as the entry point for the user interface.
 *
 * This activity sets up the primary UI components, including the navigation graph and
 * initializes the main ViewModel. It leverages Jetpack Compose for UI rendering.
 */
class MainActivity : ComponentActivity() {

    // MainViewModel late init reference
    private lateinit var viewModel: MainViewModel

    /**
     * Called when the activity is created. This method is where the app's UI is initialized.
     * It sets up edge-to-edge layout and configures the navigation component.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state,
     *                           if any. This can be used to restore the activity's state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the MainViewModel instance using Koin dependency injection
        viewModel = getViewModel()

        enableEdgeToEdge() // Enables edge-to-edge layout for the activity's content

        // Sets the content view using Jetpack Compose
        setContent {
            WebSocketTemplateTheme { // Applies the app's theme
                val navController = rememberNavController() // Creates a NavController for managing navigation
                // Sets up the navigation graph with the specified start destination
                NavGraph(
                    navController = navController,
                    startDestination = Destination.HomeScreen.route
                )
            }
        }
    }
}