package com.example.bytewisdom.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun SignInScreen(onSignIn: (String) -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue("")) }


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("ByteWisdom", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Your name") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { onSignIn(name.text.trim()) }, enabled = name.text.isNotBlank()) {
                Text("Sign in")
            }
        }
    }
}