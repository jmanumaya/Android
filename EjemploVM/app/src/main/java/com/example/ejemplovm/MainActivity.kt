package com.example.ejemplovm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ejemplovm.viewmodels.UserViewModel
import com.example.ejemplovm.ui.UserListScreen
import com.example.ejemplovm.ui.theme.EjemploVMTheme

class MainActivity : ComponentActivity() {

    // ViewModel compartido para toda la actividad
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjemploVMTheme {
                UserListScreen(userViewModel = userViewModel)
            }
        }
    }
}
