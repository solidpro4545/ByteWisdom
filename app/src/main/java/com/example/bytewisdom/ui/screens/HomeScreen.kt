package com.example.bytewisdom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.bytewisdom.ui.components.QuoteCard
import com.example.bytewisdom.viewmodel.QuoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    usernameProvider: () -> String,
    quoteState: LiveData<QuoteViewModel.UiState>,
    onGetTodayQuote: () -> Unit,
    onForceNewQuote: () -> Unit,
    onZenRandom: () -> Unit,
    onSignOut: () -> Unit
) {
    val state by quoteState.observeAsState(initial = QuoteViewModel.UiState())
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Welcome, ${usernameProvider()}") },
                actions = {
                    TextButton(onClick = onSignOut) {
                        Text("Sign out")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onGetTodayQuote) { Text("Get today’s quote") }

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            // Local/original quote (shown after first load)
            state.localQuote?.let { localQuote ->
                QuoteCard(localQuote, state.date ?: "")

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onForceNewQuote) { Text("New random quote") }
                    OutlinedButton(onClick = onZenRandom) { Text("Random from ZenQuotes") }
                }
            }

            // ZenQuotes (Today)
            state.zenQuoteToday?.let { zen ->
                Text(
                    text = "From ZenQuotes (Today):",
                    style = MaterialTheme.typography.labelLarge
                )
                QuoteCard(zen, state.date ?: "")
            }

            // ZenQuotes (Random)
            state.zenQuoteRandom?.let { zen ->
                Text(
                    text = "From ZenQuotes (Random):",
                    style = MaterialTheme.typography.labelLarge
                )
                QuoteCard(zen, state.date ?: "")
            }
        }
    }
}
