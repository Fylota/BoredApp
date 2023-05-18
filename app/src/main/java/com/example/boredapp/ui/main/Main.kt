package com.example.boredapp.ui.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boredapp.model.BoredActivity
import com.example.boredapp.ui.shared.AddActivityDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoredAppMainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onNavigateToAbout: () -> Unit,
    onNavigateToDetails: (activityKey: Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Bored App",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { expanded  = !expanded  }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded ,
                        onDismissRequest = { expanded  = false }
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info"
                            )},
                            text = { Text("About") },
                            onClick = {
                                onNavigateToAbout()
                                Toast.makeText(context, "About", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            )
        }
    ) {
        innerPadding ->
        Box(modifier = modifier.padding(innerPadding)){
            MyTab(viewModel, onNavigateToDetails)
        }
    }

}

@Composable
fun MyTab(
    viewModel: MainViewModel,
    onNavigateToDetails: (activityKey: Long) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Random", "Favourites")
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> RandomScreen(viewModel)
            1 -> FavouritesScreen(onNavigateToDetails, viewModel)
        }
    }
}

@Composable
fun RandomScreen(
    viewModel: MainViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    //var randomActivityFromAPI = viewModel.randomAPIActivity.collectAsState(initial = BoredActivity(0,"",0.0,"",0,0.0,""))
    when (viewModel.activityUiState) {
        is ActivityUiState.Loading -> LoadingScreen()
        is ActivityUiState.Success -> ResultScreen((viewModel.activityUiState as ActivityUiState.Success).result, viewModel)
        is ActivityUiState.Error -> ErrorScreen()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openDialog.value = true },
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.Add, "Add custom Activity")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {

    }

    if (openDialog.value) {
        AddActivityDialog(
            addNewActivity = { act ->
                coroutineScope.launch {
                    viewModel.addToDB(act)
                }
            },
            dialogDismiss = { openDialog.value = false },
            activity = null
        )
    }
}

@Composable
fun FavouritesScreen(
    onNavigateToDetails: (activityKey: Long) -> Unit = {},
    viewModel: MainViewModel
) {
    val activityList by viewModel.dbActivityList.collectAsState(initial = emptyList())
    LazyColumn() {
        items(activityList) {
            ActivityCard(
                activityName = it.activity,
                activityKey = it.key,
                itemClick = onNavigateToDetails
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.size(200.dp),
            text = "Loading..."
        )
    }
}

@Composable
fun ResultScreen(activity: BoredActivity, viewModel: MainViewModel) {
    val context = LocalContext.current
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 24.dp)
            ) {
            Text(
                text = "Are you bored? You should:",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
        }
        ListItem(
            headlineContent = {
                Text(activity.activity, textAlign = TextAlign.Center)
            },
            Modifier
                .padding(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Button(onClick = {
                viewModel.addToDB(activity)
                viewModel.getRandomActivity()
                Toast.makeText(context, "Activity saved", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Save")
            }
            Button(onClick = {
                viewModel.getRandomActivity()
            }) {
                Text(text = "Get another activity")
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Error...")
    }
}

@Composable
fun ActivityCard(
    activityName: String,
    activityKey: Long,
    itemClick: (activityKey: Long) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                itemClick(activityKey)
            }
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                modifier = Modifier
                    .weight(1f),
                text = activityName
            )

        }
    }
}

