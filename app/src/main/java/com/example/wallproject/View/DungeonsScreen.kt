package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.CustomAdapters.CustomAdapterDungeons
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsAdd
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDefaultScreenBinding
import com.example.wallproject.databinding.ActivityDungeonsScreenBinding

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