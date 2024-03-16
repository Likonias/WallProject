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

class DefaultScreen : AppCompatActivity() {

    private lateinit var binding: ActivityDefaultScreenBinding

    private lateinit var googleSignIn: GoogleSignIn
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    private lateinit var textHehe : TextView

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

        googleSignIn = GoogleSignIn(this)

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            googleSignIn.handleSignInResult(result.data,
                onSuccess = {
                    game.setGoogleId(googleSignIn.getCurrentUserUid())
                    game.saveGame()
                    textHehe = findViewById<TextView?>(R.id.textHehe)
                    textHehe.text =  googleSignIn.getCurrentUserUid()
                },
                onFailure = {
                    textHehe.text = "Sign in failed"
                }
            )
        }

        findViewById<SignInButton>(R.id.google_sign_in_button).setOnClickListener {
            googleSignIn.signIn(signInLauncher)
        }

        findViewById<Button>(R.id.sign_out_button).setOnClickListener {
            googleSignIn.signOut(
                onSuccess = {
                    textHehe.text = "Signed out"
                },
                onFailure = {
                    // Sign-out failed, handle the failure scenario
                }
            )
        }

        if(game.account.heroName == null){
            showPopup()
        }

        textHehe = findViewById<TextView?>(R.id.textHehe)
        textHehe.text =  googleSignIn.getCurrentUserUid()
    }

    private fun showPopup() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)

        val editTextInput = dialogView.findViewById<EditText>(R.id.editTextInput)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        builder.setPositiveButton("OK") { dialog, which ->
            val userInput = editTextInput.text.toString()
            // Process userInput as needed
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

}