package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
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

class DungeonAttackScreen : BaseActivity() {

    private lateinit var binding: ActivityDungeonAttackScreenBinding

    private var player = GameSingleton.game.dungeons.player

    private var enemy = GameSingleton.game.dungeons.currentEnemy

    private val returnToDungeonsString = "return to dungeons"
    private val hundred = 100

    private var coroutineJob: Job? = null
    private var coroutineDataUpdating: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDungeonAttackScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enemyAttackImageView.visibility = View.INVISIBLE
        binding.playerAttackImageView.visibility = View.INVISIBLE

        binding.playerNameAttackTextView.text = GameSingleton.game.dungeons.player.name
        binding.enemyNameAttackTextView.text = GameSingleton.game.dungeons.currentEnemy.name

        var currDunId = GameSingleton.game.dungeons.currentDungeon.id

        var currEnemyId = GameSingleton.game.dungeons.currentEnemy.id

        when(currDunId){

            0 -> DungeonImageMapper.dungeon1ImageMap[currEnemyId]?.let { binding.enemyImageViewAttack.setImageResource(it) }
            1 -> DungeonImageMapper.dungeon2ImageMap[currEnemyId]?.let { binding.enemyImageViewAttack.setImageResource(it) }
            2 -> DungeonImageMapper.dungeon3ImageMap[currEnemyId]?.let { binding.enemyImageViewAttack.setImageResource(it) }
            3 -> DungeonImageMapper.dungeon4ImageMap[currEnemyId]?.let { binding.enemyImageViewAttack.setImageResource(it) }
            4 -> DungeonImageMapper.dungeon5ImageMap[currEnemyId]?.let { binding.enemyImageViewAttack.setImageResource(it) }

        }

        binding.attackButton.setOnClickListener {

            if(binding.attackButton.text == returnToDungeonsString) {

                this.startActivity(Intent(this, DungeonsScreen::class.java))

            }else if(GameSingleton.game.dungeons.attackTimerZero()){

                binding.attackButton.visibility = View.INVISIBLE

                startAttackSimulation()

            }

        }

        updateDefaultHealth()

        startUpdatingData()

    }

    private fun startUpdatingData() {

        coroutineDataUpdating = GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                if(!GameSingleton.game.dungeons.attackTimerZero()){

                    binding.attackButton.text = GameSingleton.game.dungeons.dungeonsTimerSeconds.toString()

                }

                // Delay for 1 second
                delay(1000)
            }
        }
    }

    private fun startAttackSimulation() {

        coroutineDataUpdating?.cancel()

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

                    binding.attackButton.visibility = View.VISIBLE

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

                    binding.attackButton.visibility = View.VISIBLE

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