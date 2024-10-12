package com.kotlinhero.web.socket.template.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kotlinhero.web.socket.template.ui.navigation.Destination

/**
 * Composable function representing the home screen of the application.
 *
 * This screen serves as the entry point for the user, providing a welcoming message and
 * a button that navigates to the temperature screen when clicked. It uses a Scaffold to
 * layout the UI components and ensure proper padding.
 *
 * @param navController The NavController used for navigation between screens in the app.
 */
@OptIn(ExperimentalMaterial3Api::class) // Opt-in to use experimental Material3 components
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home Screen")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Welcome to the fourth Mobile-Dev-Meetup",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate(Destination.TemperatureScreen.route)
                }
            ) {
                Text(text = "View Temperature")
            }
        }
    }
}