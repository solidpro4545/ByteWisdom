package com.example.bytewisdom.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    onSignOut: () -> Unit
) {
    val state by quoteState.observeAsState(initial = QuoteViewModel.UiState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Welcome, ${usernameProvider()}") },
                actions = { TextButton(onClick = onSignOut) { Text("Sign out") } }
            )
        }
    ) { inner ->
        Column(
            Modifier.fillMaxSize().padding(inner).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Button is visible initially; tap to fetch today's quote
            Button(onClick = onGetTodayQuote) { Text("Get today’s quote") }

            if (state.isLoading) CircularProgressIndicator()

            // Show these ONLY after the first quote arrives
            state.quote?.let {
                QuoteCard(it, state.date ?: "")
                OutlinedButton(onClick = onForceNewQuote) { Text("New random quote") }
            }
        }
    }
}