package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDefaultScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DefaultScreen : AppCompatActivity() {

    private lateinit var binding: ActivityDefaultScreenBinding

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDefaultScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo finish coments

        //todo finish setting up game, loading or saving

        //using application context for some reason

        game = GameSingleton.game

        binding.profileButton.setOnClickListener {
            game.saveGame()
            startActivity(Intent(this, ProfileScreen::class.java))
        }

        if(game.account.villageName == null){
            showPopup("Enter village name: ")
            //todo finish popup logic
        }

        startUpdatingData()

    }

    private fun startUpdatingData() {
        //todo make it update the rest of the data, that needs to be updated the same way
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.spsText.text = game.tools.getSPS().toString()

                binding.wallHealthText.text = game.wall.health.toString()

                // Delay for 1 second
                delay(1000)
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
            game.account.villageName = userInput
            game.saveGame()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

}