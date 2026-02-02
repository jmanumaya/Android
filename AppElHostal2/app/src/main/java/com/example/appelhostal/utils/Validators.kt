package com.example.appelhostal.utils

object Validators {
    
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return email.isNotBlank() && emailRegex.matches(email)
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2
    }
    
    fun validateLogin(email: String, password: String): String? {
        return when {
            email.isBlank() -> "El email es requerido"
            !isValidEmail(email) -> "Email inválido"
            password.isBlank() -> "La contraseña es requerida"
            !isValidPassword(password) -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }
    
    fun validateRegister(name: String, email: String, password: String, confirmPassword: String): String? {
        return when {
            name.isBlank() -> "El nombre es requerido"
            !isValidName(name) -> "El nombre debe tener al menos 2 caracteres"
            email.isBlank() -> "El email es requerido"
            !isValidEmail(email) -> "Email inválido"
            password.isBlank() -> "La contraseña es requerida"
            !isValidPassword(password) -> "La contraseña debe tener al menos 6 caracteres"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }
}
