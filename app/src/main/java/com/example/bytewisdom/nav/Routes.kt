package com.example.bytewisdom.nav


sealed interface Route {
    data object SignIn: Route { const val route = "signin" }
    data object Home: Route { const val route = "home" }
}