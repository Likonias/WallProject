package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : BaseActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GameSingleton.game = Game(this.applicationContext)

        binding.root.setOnClickListener {
            startActivity(Intent(this, DefaultScreen::class.java))
        }

    }

}