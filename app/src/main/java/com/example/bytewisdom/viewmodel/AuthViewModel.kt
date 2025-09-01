package com.example.bytewisdom.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import com.example.bytewisdom.auth.LocalAuth
import com.example.bytewisdom.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class AuthViewModel(private val auth: LocalAuth) : ViewModel() {

    private val _isSignedIn = mutableStateOf(auth.isLoggedIn())
    val isSignedIn: State<Boolean> = _isSignedIn

    private val _username = mutableStateOf(auth.currentUsername().orEmpty())
    val username: State<String> = _username

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun login(username: String, password: String) {
        _error.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val r = auth.login(username, password)
            if (r.isSuccess) {
                _isSignedIn.value = true
                _username.value = username
            } else {
                _isSignedIn.value = false
                _error.value = r.exceptionOrNull()?.message ?: "Login failed"
            }
        }
    }

    fun register(username: String, password: String) {
        _error.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val r = auth.register(username, password)
            if (r.isSuccess) {
                _isSignedIn.value = true
                _username.value = username
            } else {
                _isSignedIn.value = false
                _error.value = r.exceptionOrNull()?.message ?: "Registration failed"
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            auth.logout()
            _isSignedIn.value = false
            _username.value = ""
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val app = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as? Application ?: throw IllegalStateException("Application missing")
                val ctx = app.applicationContext

                val db = Room.databaseBuilder(ctx, AppDatabase::class.java, "bytewisdom.db").build()
                val prefs = ctx.getSharedPreferences("bytewisdom_prefs", Context.MODE_PRIVATE)
                val auth = LocalAuth(prefs, db.userDao())
                return AuthViewModel(auth) as T
            }
        }
    }
}
