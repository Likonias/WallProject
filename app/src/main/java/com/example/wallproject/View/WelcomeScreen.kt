package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {

    //todo finish binding setup

    private lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
    }

}