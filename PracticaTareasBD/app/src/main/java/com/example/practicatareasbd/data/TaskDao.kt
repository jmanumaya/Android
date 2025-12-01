package com.example.practicatareasbd.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_entity")
    suspend fun getAllTasks(): List< Task>

    @Insert
    suspend fun addTask(task : Task): Long

    @Query("SELECT * FROM task_entity where id like :id")
    suspend fun getTareaById(id: Long): Task

    @Update
    suspend fun updateTarea(task: Task): Int

    @Delete
    suspend fun deleteTarea(task: Task): Int
}