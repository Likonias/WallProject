package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.Controller.Dungeons
import com.example.wallproject.Controller.Game
import com.example.wallproject.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var game = Game()

        val data = hashMapOf(
            "json" to game.dungeons.getJsonString()
        )

        db.collection("base")
            .add(data)


    }
}