package com.example.primerdia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.primerdia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val userAdmin:String = "admin"
    private val userPass:String = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
    // Esto ya no se hace: setContentView(R.layout.activity_main)
        setContentView(binding.root)


        binding.btnEntry.setOnClickListener {

            val user = binding.tvUser.text.toString()
            val pass = binding.tvPass.text.toString()

            if (user == userAdmin && pass == userPass) {
                val toast = Toast.makeText(
                    applicationContext,
                    "Hola $user",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            else {
                binding.errorText.text = "Usuario o contraseña incorrectos"
            }
        }
    }
}