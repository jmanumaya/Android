package com.example.ejemplovm.dal_model

object UserRepository {

    val users = mutableListOf<User>(
        User(1, "Jose"),
        User(2, "Manuel"),
        User(3, "Pepe"),
        User(4, "Juan"),
        User(5, "Maria")
    )

    fun getAllUsers(): List<User>{
        return users.toList()
    }
    fun insert(user:User){
        users.add(user)
    }
    fun insert(name:String){
        val id = users.size+1
        val user = User(id, name)
        users.add(user)
    }
}