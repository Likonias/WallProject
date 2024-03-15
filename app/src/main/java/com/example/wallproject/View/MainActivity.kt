package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wallproject.Controller.Game
import com.example.wallproject.Controller.GoogleSignIn
import com.example.wallproject.R
import com.google.android.gms.common.SignInButton

class MainActivity : AppCompatActivity() {

//    val db = Firebase.firestore
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        var game = Game(this)
//
////        val data = hashMapOf(
////            "json" to game.dungeons.getJsonString()
////        )
//// adding just base data, random id
////        db.collection("base")
////            .add(data)
////
////        val docRef = db.collection("base").document("dungeons")
////
////        var dun : MutableList<Dungeon> = mutableListOf()
////
////        docRef.get().addOnSuccessListener { document ->
////
////            val jsonText = document.getString("json")
////            val type = object : TypeToken<List<Dungeon>>() {}.type
////            dun = Gson().fromJson(jsonText, type)
////
////        }
//
//
//    }
    private lateinit var googleSignIn: GoogleSignIn
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    private lateinit var textHehe : TextView

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = Game(this)

        googleSignIn = GoogleSignIn(this)

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            googleSignIn.handleSignInResult(result.data,
                onSuccess = {
                    game.setGoogleId(googleSignIn.getCurrentUserUid())
                    game.loadGame()
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

        textHehe = findViewById<TextView?>(R.id.textHehe)
        textHehe.text =  googleSignIn.getCurrentUserUid()
    }

}