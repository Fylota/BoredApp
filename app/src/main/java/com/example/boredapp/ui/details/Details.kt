package com.example.boredapp.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boredapp.model.BoredActivity
import com.example.boredapp.ui.shared.AddActivityDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(
    activityKey: Long,
    navigateUp: () -> Unit = {},
    viewModel: DetailsViewModel,
    onNavigateToFavourites: () -> Unit
) {
   val activity by viewModel.getActivityDetails(activityKey).collectAsState(initial = BoredActivity(0,"",0.0,"",0,0.0,null))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Details",
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
                DetailsBody(activity, viewModel,onNavigateToFavourites)
            }
    }
}

@Composable
private fun DetailsBody(
    activity: BoredActivity?,
    viewModel: DetailsViewModel,
    onNavigateToFavourites: () -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val openEditDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(16.dp)) {
        if (activity != null) {
            Text(activity.activity, fontSize = 30.sp)
            Text("Type: " + activity.type)
            Text("Participants: " + activity.participants)
            Text("Price: " + activity.price)
            Text("Link: " + activity.link)
        }

        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Button(onClick = {
                openEditDialog.value = true
            }) {
                Text(text = "Edit")
            }
            Button(onClick = {
                openAlertDialog.value = true

            }) {
                Text(text = "Remove")
            }
        }
    }

    if (openAlertDialog.value) {
        RemoveAlert(
            viewModel,
            activity,
            dialogDismiss = { openAlertDialog.value = false },
            onNavigateToFavourites
        )
    }

    if (openEditDialog.value) {
        AddActivityDialog(
            addNewActivity = { act ->
                coroutineScope.launch {
                    viewModel.updateBoredActivity(act)
                }
            },
            dialogDismiss = { openEditDialog.value = false },
            activity = activity
        )
    }
}

@Composable
fun RemoveAlert(
    viewModel: DetailsViewModel,
    activity: BoredActivity?,
    dialogDismiss: () -> Unit,
    onNavigateToFavourites: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = dialogDismiss,
        title = {
            Text(text = "Delete the activity?")
        },
        text = {
            Text(text = "The activity will be permanently removed from your favourites.")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onNavigateToFavourites()
                    coroutineScope.launch {
                        if (activity != null) {
                            viewModel.removeActivity(activity)
                        }
                    }
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = dialogDismiss
            ) {
                Text("Cancel")
            }
        })
}
