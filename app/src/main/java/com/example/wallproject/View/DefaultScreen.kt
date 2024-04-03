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

        binding.researchCentreImageView.setOnClickListener {
            startActivity(Intent(this, ResearchScreen::class.java))
        }

        if(game.account.villageName == null){
            showPopup("Enter village name: ")
        }

        startUpdatingData()

        populateTools()

    }

    override fun onResume() {
        super.onResume()
        populateTools() // Refresh tool icons when the screen becomes visible again
    }

    //this prevents to go back to the welcome screen
    override fun onBackPressed() {}

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                if(game.wall.gameOver){
                    showGameOverPopup("You have finished the game! Do you want to start over or continue into the infinity mode?")
                }

                binding.spsText.text = game.tools.getSPS().toString()

                binding.wallHealthText.text = game.wall.getCurrentHealth().toString()

                binding.villageNameText.text = game.account.villageName

                binding.stonesText.text = game.wallet.getBalanceInt().toString()

                if(!binding.dungeonImageView.isVisible && game.dungeons.isAnyDiscovered()){
                    binding.dungeonImageView.visibility = View.VISIBLE
                }

                // Delay for 1 second
                delay(1000)
            }
        }
    }

    private fun populateTools() {
        //refreshes the data inside the linear layout
        binding.toolsLinearLayout.removeAllViews()

        val linearLayout: LinearLayout = binding.toolsLinearLayout
        val screenWidth = resources.displayMetrics.widthPixels
        val maxToolWidthInPixels = (50 * resources.displayMetrics.density).toInt() // Maximum width for each tool in pixels

        val toolsCount = game.tools.getActiveToolsCount()

        if (toolsCount > 0) {
            // Calculate the available width for each tool
            val availableWidth = screenWidth / toolsCount

            for (tool in game.tools.getTools()) {
                if (tool.isDiscovered && tool.count > 0) {
                    val imageView = ImageView(this)
                    val toolDrawableId = ToolImageMapper.toolImageMap[tool.id]

                    if (toolDrawableId != null) {

                        val widthInPixels = if (availableWidth > maxToolWidthInPixels)
                            maxToolWidthInPixels
                        else
                            availableWidth

                        val heightInPixels = widthInPixels

                        imageView.setImageResource(toolDrawableId)

                        val params = LinearLayout.LayoutParams(widthInPixels, heightInPixels)
                        imageView.layoutParams = params

                        linearLayout.addView(imageView)
                    }
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

    private fun showGameOverPopup(prompt : String) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)

        dialogView.findViewById<TextView>(R.id.textPrompt).text = prompt

        val editTextInput = dialogView.findViewById<EditText>(R.id.editTextInput)

        editTextInput.visibility = View.INVISIBLE

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        builder.setPositiveButton("Start Over") { dialog, which ->
            game = Game(applicationContext)
        }

        builder.setNegativeButton("Infinity!!!") { dialog, which ->
            game.wall.gameOver = false
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

}