package com.example.wallproject.View

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Model.CustomAdapters.CustomAdapterDungeons
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDungeonsScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DungeonsScreen : BaseActivity() {

    private lateinit var binding: ActivityDungeonsScreenBinding
    private lateinit var customAdapterDungeons: CustomAdapterDungeons
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDungeonsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapterDungeons = CustomAdapterDungeons(this, GameSingleton.game.dungeons.getDiscoveredDungeons())

        binding.recyclerViewDungeons.layoutManager = LinearLayoutManager(this)

        // Attach the adapter to the RecyclerView
        binding.recyclerViewDungeons.adapter = customAdapterDungeons

        if(GameSingleton.game.account.heroName == null){
            showPopup("Enter hero name: ")
        }

        binding.levelUpButton.setOnClickListener {

            if (!GameSingleton.game.levelUpPlayer()) {

                Toast.makeText(applicationContext, "Not enough stone!", Toast.LENGTH_SHORT).show()

            }

        }

        binding.addAdButton.setOnClickListener {

            if(!GameSingleton.game.updatePlayerAd()) {

                Toast.makeText(applicationContext, "Not enough upgrade ponts!", Toast.LENGTH_SHORT).show()

            }

        }

        binding.addApButton.setOnClickListener {

            if(!GameSingleton.game.updatePlayerAp()) {

                Toast.makeText(applicationContext, "Not enough upgrade ponts!", Toast.LENGTH_SHORT).show()

            }

        }

        binding.addArmorButton.setOnClickListener {

            if(!GameSingleton.game.updatePlayerArmor()) {

                Toast.makeText(applicationContext, "Not enough upgrade ponts!", Toast.LENGTH_SHORT).show()

            }

        }

        binding.addMrButton.setOnClickListener {

            if(!GameSingleton.game.updatePlayerMagicResist()) {

                Toast.makeText(applicationContext, "Not enough upgrade ponts!", Toast.LENGTH_SHORT).show()

            }

        }

        binding.addLuckButton.setOnClickListener {

            if(!GameSingleton.game.updatePlayerLuck()) {

                Toast.makeText(applicationContext, "Not enough upgrade ponts!", Toast.LENGTH_SHORT).show()

            }

        }


        startUpdatingData()

    }

    override fun onBackPressed() {

        this.startActivity(Intent(this, DefaultScreen::class.java))

    }

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                val player = GameSingleton.game.dungeons.player

                binding.playerNameTextView.text = player.name

                binding.stonesTextViewDungeons.text = GameSingleton.game.wallet.getBalanceFormated().toString()

                binding.levelUpCostTextView.text = player.levelUpCost.toString()

                binding.attackDamageTextView.text = player.attack.attackDmg.toString()

                binding.abilityPowerTextView.text = player.attack.abilityPower.toString()

                binding.armorTextView.text = player.defense.armor.toString()

                binding.magicResistTextView.text = player.defense.magicResist.toString()

                binding.luckTextView.text = player.attack.luck.toString()

                binding.pointsToSpendTextView.text = player.pointsToSpend.toString()

                // Delay for 0,1 second for better responsivity
                delay(100)
            }
        }
    }

    private fun showPopup(prompt : String) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)

        dialogView.findViewById<TextView>(R.id.textPrompt).text = prompt

        val editTextInput = dialogView.findViewById<EditText>(R.id.editTextInput)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        builder.setPositiveButton("OK") { dialog, which ->
            val userInput = editTextInput.text.toString()
            GameSingleton.game.initializeHeroName(userInput)
            GameSingleton.game.saveGame()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }
}