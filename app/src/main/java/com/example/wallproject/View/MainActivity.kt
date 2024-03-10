package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.Controller.Dungeons
import com.example.wallproject.Controller.Game
import com.example.wallproject.Model.Dungeon.Dungeon
import com.example.wallproject.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var game = Game(this)

//        val data = hashMapOf(
//            "json" to game.dungeons.getJsonString()
//        )
// adding just base data, random id
//        db.collection("base")
//            .add(data)
//
//        val docRef = db.collection("base").document("dungeons")
//
//        var dun : MutableList<Dungeon> = mutableListOf()
//
//        docRef.get().addOnSuccessListener { document ->
//
//            val jsonText = document.getString("json")
//            val type = object : TypeToken<List<Dungeon>>() {}.type
//            dun = Gson().fromJson(jsonText, type)
//
//        }


    }
}