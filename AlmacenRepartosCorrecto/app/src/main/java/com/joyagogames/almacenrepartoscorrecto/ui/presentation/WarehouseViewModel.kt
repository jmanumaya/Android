package com.joyagogames.almacenrepartoscorrecto.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joyagogames.almacenrepartoscorrecto.data.repository.WarehouseRepository
import com.joyagogames.almacenrepartoscorrecto.domain.entities.Article

class WarehouseViewModel : ViewModel() {

    private val repo = WarehouseRepository()

    var boxes by mutableStateOf(repo.getBoxes())
        private set

    fun addArticle(box: Int, article: Article): Boolean {
        val success = repo.addArticle(box, article)
        if (success) boxes = repo.getBoxes()
        return success
    }

    fun deleteArticle(box: Int, id: Int) {
        repo.deleteArticle(box, id)
        boxes = repo.getBoxes()
    }

    fun clearBox(box: Int) {
        repo.clearBox(box)
        boxes = repo.getBoxes()
    }
}
