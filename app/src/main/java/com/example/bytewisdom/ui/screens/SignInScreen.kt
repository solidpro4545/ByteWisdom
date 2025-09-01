package com.example.bytewisdom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(
    onSignIn: (username: String, password: String) -> Unit,
    onNavigateRegister: () -> Unit,
    errorText: String? = null
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onSignIn(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }

        TextButton(
            onClick = onNavigateRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create an account")
        }

        errorText?.let {
            Spacer(Modifier.height(12.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
