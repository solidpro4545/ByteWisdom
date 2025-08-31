package com.example.bytewisdom.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.mutableStateOf


class AuthViewModel: ViewModel() {
    val isSignedIn = mutableStateOf(false)
    val username = mutableStateOf("")


    fun signIn(name: String) {
        username.value = name.ifBlank { "Friend" }
        isSignedIn.value = true
    }
    fun signOut() { isSignedIn.value = false; username.value = "" }


    object Factory: ViewModelProvider.Factory { override fun <T : ViewModel> create(modelClass: Class<T>): T = AuthViewModel() as T }
}