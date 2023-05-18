package com.example.boredapp.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.boredapp.model.BoredActivity

@Composable
fun AddActivityDialog(addNewActivity: (BoredActivity) -> Unit,
                      dialogDismiss: () -> Unit,
                      activity: BoredActivity?
) {
    val editActivity: BoredActivity = activity ?: BoredActivity(0,"",0.0,"",0,0.0,"")

    Dialog(onDismissRequest = dialogDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                val key by remember { mutableStateOf(editActivity.key.toString()) }
                var title by remember { mutableStateOf(editActivity.activity) }
                var accessibility by remember { mutableStateOf(editActivity.accessibility.toString()) }
                var type by remember { mutableStateOf(editActivity.type) }
                var participants by remember { mutableStateOf(editActivity.participants.toString()) }
                var price by remember { mutableStateOf(editActivity.price.toString()) }
                var link by remember { mutableStateOf(editActivity.link.toString()) }

                OutlinedTextField(
                    value = title,
                    label = { Text(text = "Activity Title") },
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = accessibility,
                    label = { Text(text = "Activity Accessibility") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        accessibility = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = type,
                    label = { Text(text = "Activity Type") },
                    onValueChange = {
                        type = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = participants,
                    label = { Text(text = "Number of participants") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        participants = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = price,
                    label = { Text(text = "Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        price = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = link,
                    label = { Text(text = "Link") },
                    onValueChange = {
                        link = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(onClick = {
                    addNewActivity(BoredActivity(key.toLong(),title, accessibility.toDouble(),type,participants.toInt(),price.toDouble(),link))
                    dialogDismiss()
                }) {
                    Text(text = "Save Activity")
                }
            }
        }
    }
}
