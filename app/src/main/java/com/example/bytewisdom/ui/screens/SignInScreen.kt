package com.example.bytewisdom.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.bytewisdom.R

@Composable
fun SignInScreen(
    onSignIn: (username: String, password: String) -> Unit,
    onNavigateRegister: () -> Unit,
    errorText: String? = null
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.headlineMedium,
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
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Sign In")
            }

            TextButton(
                onClick = onNavigateRegister,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Create an account")
            }

            errorText?.let {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Icon(
            painter = painterResource(R.drawable.ic_instagram),
            contentDescription = "Instagram",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(32.dp)
                .clickable { openInstagram(context) }
        )
    }
}

private fun openInstagram(context: Context) {
    val uri = Uri.parse("https://www.instagram.com/bytewisdom_motivation/")
    val instagramIntent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.instagram.android")
    }
    try {
        context.startActivity(instagramIntent)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
