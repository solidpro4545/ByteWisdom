package com.example.bytewisdom

import android.app.Application
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bytewisdom.nav.Route
import com.example.bytewisdom.ui.screens.HomeScreen
import com.example.bytewisdom.ui.screens.SignInScreen
import com.example.bytewisdom.ui.screens.RegisterScreen
import com.example.bytewisdom.viewmodel.AuthViewModel
import com.example.bytewisdom.viewmodel.QuoteViewModel

@Composable
fun ByteWisdomApp(app: Application) {
    val nav = rememberNavController()

    // Keep your existing factories
    val authVm: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
    val quoteVm: QuoteViewModel = viewModel(factory = QuoteViewModel.factory(app))

    val isSignedIn by authVm.isSignedIn
    LaunchedEffect(isSignedIn) {
        nav.navigate(if (isSignedIn) Route.Home.route else Route.SignIn.route) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    MaterialTheme {
        NavHost(navController = nav, startDestination = Route.SignIn.route) {

            // SIGN IN
            composable(Route.SignIn.route) {
                SignInScreen(
                    onSignIn = { username, password -> authVm.login(username, password) },
                    onNavigateRegister = { nav.navigate("register") },
                    errorText = authVm.error.value
                )
            }

            // REGISTER
            composable("register") {
                RegisterScreen(
                    onRegister = { username, password -> authVm.register(username, password) },
                    onNavigateBack = { nav.popBackStack() },
                    errorText = authVm.error.value
                )
            }

            // HOME
            composable(Route.Home.route) {
                HomeScreen(
                    usernameProvider = { authVm.username.value.ifBlank { "Friend" } },
                    quoteState = quoteVm.quoteState,
                    onGetTodayQuote = { quoteVm.loadTodayQuote() },
                    onForceNewQuote = { quoteVm.forceNewQuote() },
                    onZenRandom = { quoteVm.fetchZenRandom() },
                    onSignOut = {
                        authVm.signOut()
                        quoteVm.clearQuote()
                    }
                )
            }
        }
    }
}
