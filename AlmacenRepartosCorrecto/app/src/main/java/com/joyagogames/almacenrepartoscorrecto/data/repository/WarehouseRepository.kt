package com.joyagogames.almacenrepartoscorrecto.data.repository

import com.joyagogames.almacenrepartoscorrecto.domain.entities.Article
import com.joyagogames.almacenrepartoscorrecto.domain.entities.Box

class WarehouseRepository {

    private val boxes = mutableListOf(
        Box(1), Box(2), Box(3), Box(4), Box(5), Box(6)
    )

    fun getBoxes(): List<Box> = boxes

    fun addArticle(boxNumber: Int, article: Article): Boolean {
        val index = boxNumber - 1
        val box = boxes[index]

        if (box.totalWeight + article.totalWeight > 21) return false

        boxes[index] = box.copy(
            articles = box.articles + article
        )
        return true
    }

    fun deleteArticle(boxNumber: Int, articleId: Int) {
        val index = boxNumber - 1
        val box = boxes[index]

        boxes[index] = box.copy(
            articles = box.articles.filterNot { it.id == articleId }
        )
    }

    fun clearBox(boxNumber: Int) {
        boxes[boxNumber - 1] = Box(boxNumber)
    }
}
