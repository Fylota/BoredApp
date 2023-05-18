package com.example.boredapp.ui.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navigateUp: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "About",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
            innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            AboutBody()
        }
    }
}

@Composable()
fun AboutBody() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("About this app", textAlign = TextAlign.Center, fontSize = 30.sp)
        Text(text = "This android app was made for Mobile Software Laboratory Course at Budapest University of Technology and Economics in the 2022/23 spring semester.",
        textAlign = TextAlign.Justify)
    }
    
}
