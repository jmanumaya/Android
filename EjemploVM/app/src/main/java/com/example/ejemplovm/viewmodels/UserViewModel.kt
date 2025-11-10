package com.example.ejemplovm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.ejemplovm.dal_model.User
import com.example.ejemplovm.dal_model.UserRepository

class UserViewModel : ViewModel() {
    private val _repo = UserRepository
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _users.value = _repo.getAllUsers()
    }
    fun insertUser(user:User){
        _repo.insert(user)
        loadUsers()
    }
}

