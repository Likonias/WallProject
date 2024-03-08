package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Dungeon.Characters.Attack
import com.example.wallproject.Model.Dungeon.Characters.Defense
import com.example.wallproject.Model.Dungeon.Characters.Enemy
import com.example.wallproject.Model.Dungeon.Dungeon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream

class Dungeons(context: Context) {

    private val dungeonsPathName = "dungeons.json"

    private var dungeons : MutableList<Dungeon> = mutableListOf()

    private var context = context

    init {
        //todo make sure to fix this
        loadDungeons()
        var enemies : MutableList<Enemy> = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(1, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        dungeons.add(Dungeon(0, enemies, "Name", "Descript"))
        enemies.add(Enemy(0, "Zombie1", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(1, "Zombie Strong2", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        dungeons.add(Dungeon(1, enemies, "Dun2", "Descript2"))
        saveDungeons()

    }

    fun loadDungeons(){

        context.openFileInput(dungeonsPathName).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
    }

    fun saveDungeons() {

        val jsonString = Gson().toJson(dungeons)
        context.openFileOutput(dungeonsPathName, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }

    }

    fun getJsonString() : String{
        return Gson().toJson(dungeons).toString()
    }

    fun getEnemyFromDungeon(dungeonId : Int) : Enemy? {

        return dungeons.get(dungeonId).getCurrentEnemy()

    }

}