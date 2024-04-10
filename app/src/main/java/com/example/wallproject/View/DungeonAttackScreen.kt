package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.DungeonImageMapper
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.Model.ToolImageMapper
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDungeonAttackScreenBinding
import com.example.wallproject.databinding.ActivityDungeonsScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlinx.coroutines.*

class DungeonAttackScreen : AppCompatActivity() {

    private lateinit var binding: ActivityDungeonAttackScreenBinding

    private var player = GameSingleton.game.dungeons.player

    private var enemy = GameSingleton.game.dungeons.currentEnemy

    private val returnToDungeonsString = "return to dungeons"
    private val hundred = 100

    private var coroutineJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDungeonAttackScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enemyAttackImageView.visibility = View.INVISIBLE
        binding.playerAttackImageView.visibility = View.INVISIBLE

        var currDunId = GameSingleton.game.dungeons.currentDungeon.id

        when(currDunId){

            0 -> DungeonImageMapper.dungeon1ImageMap[currDunId]?.let { binding.playerAttackImageView.setImageResource(it) }
            1 -> DungeonImageMapper.dungeon2ImageMap[currDunId]?.let { binding.playerAttackImageView.setImageResource(it) }
            2 -> DungeonImageMapper.dungeon3ImageMap[currDunId]?.let { binding.playerAttackImageView.setImageResource(it) }
            3 -> DungeonImageMapper.dungeon4ImageMap[currDunId]?.let { binding.playerAttackImageView.setImageResource(it) }
            4 -> DungeonImageMapper.dungeon5ImageMap[currDunId]?.let { binding.playerAttackImageView.setImageResource(it) }

        }

        binding.attackButton.setOnClickListener {

            if(binding.attackButton.text == returnToDungeonsString) {

                this.startActivity(Intent(this, DungeonsScreen::class.java))

            }else{

                startAttackSimulation()

            }

        }

        updateDefaultHealth()

    }

    private fun startAttackSimulation() {
        coroutineJob?.cancel() // Cancel any existing coroutine job

        coroutineJob = CoroutineScope(Dispatchers.Main).launch {
            attackSimulation()
        }
    }

    private suspend fun attackSimulation() {

        var randNum = Random.nextInt(0, 10)

        while (true) {

            delay(500)

            if(randNum % 2 == 0){

                var enemyHealth = GameSingleton.game.dungeonPlayerAttack()

                updateEnemyHealth(enemyHealth)

                binding.playerAttackImageView.visibility = View.VISIBLE

                delay(100)

                binding.playerAttackImageView.visibility = View.INVISIBLE

                if(enemyHealth <= 0){

                    binding.attackButton.text = returnToDungeonsString

                    if(GameSingleton.game.toolHasBeenDiscovered){

                        Toast.makeText(applicationContext, "A new tool has been discovered!", Toast.LENGTH_SHORT).show()

                    }

                    break
                }

            }else{

                var heroHealth = GameSingleton.game.dungeonEnemyAttack()

                updatePlayerHealth(heroHealth)

                binding.enemyAttackImageView.visibility = View.VISIBLE

                delay(100)

                binding.enemyAttackImageView.visibility = View.INVISIBLE

                if(heroHealth <= 0){

                    binding.attackButton.text = returnToDungeonsString

                    break

                }

            }

            randNum++

        }
    }

    private fun updateDefaultHealth() {

        binding.enemyProgressBar.setProgress(hundred)

        binding.playerProgressBar.setProgress(hundred)

    }

    private fun updateEnemyHealth(currentHealth: Double) {

        binding.enemyProgressBar.setProgress(getEnemyHealthPercentage(currentHealth))

    }

    private fun updatePlayerHealth(currentHealth: Double) {

        binding.playerProgressBar.setProgress(getPlayerHealthPercentage(currentHealth))

    }

    private fun getEnemyHealthPercentage(currentHealth : Double) : Int {
        return (100 * currentHealth / enemy.health).toInt()
    }

    private fun getPlayerHealthPercentage(currentHealth : Double) : Int {
        return (100 * currentHealth / player.health).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineJob?.cancel()
    }

}