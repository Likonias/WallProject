package com.example.wallproject.Controller

import com.example.wallproject.Model.Dungeon.Characters.Attack
import com.example.wallproject.Model.Dungeon.Characters.Defense
import com.example.wallproject.Model.Dungeon.Characters.Enemy
import com.example.wallproject.Model.Dungeon.Dungeon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class Dungeons() {

    private val dungeonsPathName = "app/src/main/java/com/example/wallproject/Model/JSON/dungeons.json"

    var dungeons : MutableList<Dungeon> = mutableListOf()

    init {
        loadDungeons()
        var enemies : MutableList<Enemy> = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 100.0, Attack(1.0, 1.0, 1.0), Defense(10.0, 15.0) ))
        dungeons.add(Dungeon(0, enemies, "Name", "Descript"))
        saveDungeons()
    }

    private fun loadDungeons(){
        val jsonString = File(dungeonsPathName).readText()
        val listType = object : TypeToken<MutableList<Dungeon>>() {}.type
        dungeons = Gson().fromJson(jsonString, listType)
    }

    fun saveDungeons() {
        val jsonString = Gson().toJson(dungeons)
        File(dungeonsPathName).writeText(jsonString)
    }

}