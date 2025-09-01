package com.example.bytewisdom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onRegister: (username: String, password: String) -> Unit,
    onNavigateBack: () -> Unit,
    errorText: String? = null
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.padding(24.dp)) {
        Text("Create Account", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, singleLine = true)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") },
            singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onRegister(username, password) }) { Text("Register") }
        TextButton(onClick = onNavigateBack) { Text("Back to Sign In") }
        errorText?.let { Spacer(Modifier.height(8.dp)); Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
