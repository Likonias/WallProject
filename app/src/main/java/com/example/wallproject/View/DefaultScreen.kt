package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.wallproject.Controller.Game
import com.example.wallproject.Controller.GoogleSignIn
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDefaultScreenBinding
import com.google.android.gms.common.SignInButton
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
        GameSingleton.game = Game(this.applicationContext)

        game = GameSingleton.game

        game.loadGame()

        binding.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileScreen::class.java))
        }

        if(game.account.heroName == null){
            showPopup("Enter hero name: ")
            //todo finish popup logic
        }

        startUpdatingSPSText()

    }

    private fun startUpdatingSPSText() {
        //todo make it update the rest of the data, that needs to be updated the same way
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                // Update spsText
                binding.spsText.text = game.wall.health.toString()

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
            game.account.heroName = userInput
            game.saveGame()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

}