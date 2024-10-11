package com.kotlinhero.web.socket.template.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kotlinhero.web.socket.template.data.models.TemperatureModel
import com.kotlinhero.web.socket.template.domain.states.SocketConnection

/**
 * Composable function representing the temperature screen of the application.
 *
 * This screen displays the current temperature and the socket connection status.
 * It features a top app bar with a navigation button to return to the previous screen.
 *
 * @param temperature The current temperature data model, which can be null.
 * @param socketConnection The current state of the socket connection, used to display
 *                         appropriate messages or indicators.
 * @param navController The NavController used for navigation between screens in the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureScreen(
    temperature: TemperatureModel?,
    socketConnection: SocketConnection,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text("Temperature Screen")
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (socketConnection) {
                SocketConnection.Connecting -> CircularProgressIndicator()
                SocketConnection.Disconnected -> Text(
                    "Socket Disconnected",
                    style = MaterialTheme.typography.titleMedium,
                )
                SocketConnection.Connected -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Socket is connected, expecting values soon",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    temperature?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Temperature: ${temperature.value}${temperature.unit}")
                    }
                }
            }
        }
    }
}