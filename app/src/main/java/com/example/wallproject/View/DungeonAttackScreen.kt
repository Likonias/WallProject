package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDungeonAttackScreenBinding
import com.example.wallproject.databinding.ActivityDungeonsScreenBinding

class DungeonAttackScreen : AppCompatActivity() {

    private lateinit var binding: ActivityDungeonAttackScreenBinding

    private var player = GameSingleton.game.dungeons.player

    private var enemy = GameSingleton.game.dungeons.currentEnemy

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDungeonAttackScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateHealth()

    }

    private fun updateHealth() {

        binding.enemyProgressBar.setProgress(getEnemyHealthPercentage())

        binding.playerProgressBar.setProgress(getPlayerHealthPercentage())

    }

    private fun getEnemyHealthPercentage() : Int {
        return (100 * enemy.currentHealth / enemy.health).toInt()
    }

    private fun getPlayerHealthPercentage() : Int {
        return (100 * player.currentHealth / player.health).toInt()
    }

}