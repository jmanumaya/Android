package com.example.appelhostal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostal.data.entities.ClientEntity
import com.example.appelhostal.data.repository.AuthRepository
import com.example.appelhostal.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    
    private val _currentUser = MutableStateFlow<ClientEntity?>(null)
    val currentUser: StateFlow<ClientEntity?> = _currentUser.asStateFlow()
    
    private val _loginState = MutableStateFlow<UiState<ClientEntity>>(UiState.Idle)
    val loginState: StateFlow<UiState<ClientEntity>> = _loginState.asStateFlow()
    
    private val _registerState = MutableStateFlow<UiState<ClientEntity>>(UiState.Idle)
    val registerState: StateFlow<UiState<ClientEntity>> = _registerState.asStateFlow()
    
    val isLoggedIn: Boolean
        get() = _currentUser.value != null
    
    val isOwner: Boolean
        get() = _currentUser.value?.isOwner == true
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val client = authRepository.login(email, password)
                if (client != null) {
                    _currentUser.value = client
                    _loginState.value = UiState.Success(client)
                } else {
                    _loginState.value = UiState.Error("Email o contraseña incorrectos")
                }
            } catch (e: Exception) {
                _loginState.value = UiState.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }
    
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            val result = authRepository.register(name, email, password)
            result.fold(
                onSuccess = { client ->
                    _currentUser.value = client
                    _registerState.value = UiState.Success(client)
                },
                onFailure = { error ->
                    _registerState.value = UiState.Error(error.message ?: "Error al registrar")
                }
            )
        }
    }
    
    fun logout() {
        _currentUser.value = null
        _loginState.value = UiState.Idle
        _registerState.value = UiState.Idle
    }
    
    fun resetLoginState() {
        _loginState.value = UiState.Idle
    }
    
    fun resetRegisterState() {
        _registerState.value = UiState.Idle
    }
}
