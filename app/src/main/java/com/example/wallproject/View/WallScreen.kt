package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDefaultScreenBinding
import com.example.wallproject.databinding.ActivityWallScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WallScreen : AppCompatActivity() {

    private lateinit var binding: ActivityWallScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startUpdatingData()
    }

    private fun startUpdatingData() {
        //todo make it update the rest of the data, that needs to be updated the same way
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.wallHealthText.text = GameSingleton.game.wall.health.toString()

                binding.shellsText.text = GameSingleton.game.wall.shells.toString()

                // Delay for 1 second
                delay(1000)
            }
        }
    }

}