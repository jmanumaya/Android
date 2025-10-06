package com.example.primerdia

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primerdia.databinding.ActivityMainBinding
import com.example.primerdia.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra("user")

        binding.textView3.text = "Bienvenido $user"

        binding.butNav.setOnClickListener {
            var text = binding.editTextMain.text.toString()
            buscador(text)
        }

        binding.butTel.setOnClickListener {
            var tel = binding.editTextMain.text.toString()
            llamar(tel)
        }

        binding.butMen.setOnClickListener {
            var message = binding.editTextMain.text.toString()
            mensaje(message)
        }

        binding.butCom.setOnClickListener {
            var text = binding.editTextMain.text.toString()
            compartir(text)
        }
    }

    fun buscador(text : String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = "https://www.google.com/search?q=${text}".toUri()
        startActivity(intent)
    }

    fun llamar(tel : String){
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data="tel:${tel}".toUri()
        startActivity(callIntent)
    }

    fun mensaje(message : String){
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("smsto:" + message)
        smsIntent.putExtra("sms_body","Hola desde mi app")
        startActivity(smsIntent)
    }

    fun compartir(text : String){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(shareIntent, "Compartir con..."))
    }
}