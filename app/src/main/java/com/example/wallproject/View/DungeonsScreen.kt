package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.CustomAdapters.CustomAdapterDungeons
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsAdd
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDefaultScreenBinding
import com.example.wallproject.databinding.ActivityDungeonsScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DungeonsScreen : AppCompatActivity() {

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

        }


        startUpdatingData()

    }

    private fun startUpdatingData() {
        //todo make it update the rest of the data, that needs to be updated the same way
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                val player = GameSingleton.game.dungeons.player

                binding.playerNameTextView.text = player.name

                binding.stonesTextViewDungeons.text = GameSingleton.game.wallet.getBalanceInt().toString()

                binding.levelUpCostTextView.text = player.levelUpCost.toString()

                binding.attackDamageTextView.text = player.attack.attackDmg.toString()

                binding.abilityPowerTextView.text = player.attack.abilityPower.toString()

                binding.armorTextView.text = player.defense.armor.toString()

                binding.magicResistTextView.text = player.defense.magicResist.toString()

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