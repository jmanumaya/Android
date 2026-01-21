package com.joyagogames.ejercicio1ppt.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joyagogames.ejercicio1ppt.data.GameRepository

class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(repository) as T
    }
}