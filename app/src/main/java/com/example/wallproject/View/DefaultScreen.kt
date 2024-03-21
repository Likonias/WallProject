package com.example.wallproject.View

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.Model.ToolImageMapper
import com.example.wallproject.Model.Wall.Wall
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

        binding.wallImageView.setOnClickListener {
            startActivity(Intent(this, WallScreen::class.java))
        }

        binding.dungeonImageView.setOnClickListener {
            startActivity(Intent(this, DungeonsScreen::class.java))
        }

        binding.toolsLinearLayout.setOnClickListener {
            startActivity(Intent(this, ToolsScreen::class.java))
        }

        if(game.account.villageName == null){
            showPopup("Enter village name: ")
            //todo finish popup logic
        }

        startUpdatingData()

        populateTools()

    }

    //this prevents to go back to the welcome screen
    override fun onBackPressed() {}

    private fun startUpdatingData() {
        //todo make it update the rest of the data, that needs to be updated the same way
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.spsText.text = game.tools.getSPS().toString()

                binding.wallHealthText.text = game.wall.health.toString()

                binding.villageNameText.text = game.account.villageName

                if(!binding.dungeonImageView.isVisible && game.dungeons.isAnyDiscovered()){
                    binding.dungeonImageView.visibility = View.VISIBLE
                }

                // Delay for 1 second
                delay(1000)
            }
        }
    }

    private fun populateTools() {
        val linearLayout: LinearLayout = binding.toolsLinearLayout

         for(tool in game.tools.getTools()) {

             if(tool.isDiscovered){

                 val imageView = ImageView(this)

                 val toolDrawableId = ToolImageMapper.toolImageMap[tool.id]

                 if (toolDrawableId != null) {

                     imageView.setImageResource(toolDrawableId)

                     imageView.layoutParams = LinearLayout.LayoutParams(
                         LinearLayout.LayoutParams.WRAP_CONTENT,
                         LinearLayout.LayoutParams.WRAP_CONTENT,
                         )

                     linearLayout.addView(imageView)
                 }

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