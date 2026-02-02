package com.example.appelhostalbien.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostalbien.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    val currentUser = repository.currentUser

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = repository.login(email, password)
            if (success) {
                _loginError.value = null
                onSuccess()
            } else {
                _loginError.value = "Email o contraseña incorrectos"
            }
        }
    }

    fun register(name: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = repository.register(name, email, password)
            if (success) {
                onSuccess()
            } else {
                _loginError.value = "Error al registrarse"
            }
        }
    }

    fun logout() {
        repository.logout()
    }
}
