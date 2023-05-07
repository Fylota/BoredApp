package com.example.boredapp.ui.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BoredAppMainScreen() {
    Text("Main screen")
}

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")
}

@Preview
@Composable
fun SimpleComposablePreview() {
    BoredAppMainScreen()
}
