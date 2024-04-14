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

class WallScreen : BaseActivity() {

    private lateinit var binding: ActivityWallScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startUpdatingData()
    }

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.wallHealthText.text = GameSingleton.game.wall.getCurrentHealth().toString()

                binding.shellsText.text = GameSingleton.game.wall.shells.toString()

                // Delay for 1 second
                delay(1000)
            }
        }
    }

}