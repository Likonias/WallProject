package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R

open class BaseActivity : AppCompatActivity() {
    override fun onDestroy() {
        super.onDestroy()
        GameSingleton.game.saveGame()
    }
}