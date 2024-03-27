package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Dungeon.Characters.Attack
import com.example.wallproject.Model.Dungeon.Characters.Defense
import com.example.wallproject.Model.Dungeon.Characters.Enemy
import com.example.wallproject.Model.Dungeon.Characters.Player
import com.example.wallproject.Model.Dungeon.Dungeon
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream

class Dungeons(@Transient private var context: Context) {

    private val dungeonsPathName = "dungeons.json"

    private var dungeons : MutableList<Dungeon> = mutableListOf()

    @Transient private val db = Firebase.firestore
    @Transient private val docRef = db.collection("base").document("dungeons")

    private lateinit var player : Player
    private lateinit var currentEnemy : Enemy

    var currentDungeon : Int = 0

    init {
        //todo make sure to fix this
        loadDungeons()
//        var enemies : MutableList<Enemy> = mutableListOf()
//        enemies.add(Enemy(0, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
//        enemies.add(Enemy(1, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
//        dungeons.add(Dungeon(0, enemies, "Name", "Descript"))
//        enemies.add(Enemy(0, "Zombie1", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
//        enemies.add(Enemy(1, "Zombie Strong2", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
//        dungeons.add(Dungeon(1, enemies, "Dun2", "Descript2"))
        //saveDungeons()

    }

    fun initializePlayer(playerName : String){
        player = Player(playerName)
    }

    fun getEnemyFromDungeon(dungeonId : Int) : Enemy? {

        currentDungeon = dungeonId

        return dungeons.get(dungeonId).getCurrentEnemy()

    }

    fun attackEnemyFromDungeon(dungeonId: Int) {

        currentDungeon = dungeonId

        currentEnemy = getEnemyFromDungeon(dungeonId)!!

    }

    internal fun playerAttack() : Double {

        var healthAfterAttack = player.attack(currentEnemy)

        if (currentEnemy.isDead())
            player.resetHealth()

        return healthAfterAttack

    }

    internal fun enemyAttack() : Double {

        var healthAfterAttack = currentEnemy.attack(player)

        if(player.isDead()) {
            player.resetHealth()
            currentEnemy.resetHealth()
        }

        return healthAfterAttack

    }

    fun discoverNext() {

        dungeons.forEach { dungeon ->
            if(!dungeon.isDiscovered){
                dungeon.isDiscovered = true
                return
            }
        }

    }

    fun isAnyDiscovered() : Boolean {

        dungeons.forEach { dungeon ->
            if(dungeon.isDiscovered){
                return true
            }
        }

        return false

    }

    fun getCurrentEnemyId() : Int {

        return currentEnemy.id

    }

    fun getDungeons() : MutableList<Dungeon> {

        return dungeons

    }

    private fun loadDungeons(){

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists()) {
                    val jsonText = document.getString("json")
                    if (jsonText != null) {
                        val type = object : TypeToken<MutableList<Dungeon>>() {}.type
                        dungeons = Gson().fromJson(jsonText, type)
                    }
                }
            }

            // If loading from Firestore fails or there's no data, try loading from local storage
            if (dungeons.isEmpty()) {
                try {
                    val inputStream: InputStream = context.openFileInput(dungeonsPathName)
                    val jsonText = inputStream.bufferedReader().use { it.readText() }
                    val type = object : TypeToken<MutableList<Dungeon>>() {}.type
                    dungeons = Gson().fromJson(jsonText, type)
                } catch (e: Exception) {
                    // Handle exceptions, e.g., file not found, JSON parsing error
                    e.printStackTrace()
                }
            }
        }
            .addOnFailureListener {
                // Attempt to load from local storage as a fallback
                try {
                    val inputStream: InputStream = context.openFileInput(dungeonsPathName)
                    val jsonText = inputStream.bufferedReader().use { it.readText() }
                    val type = object : TypeToken<MutableList<Dungeon>>() {}.type
                    dungeons = Gson().fromJson(jsonText, type)
                } catch (e: Exception) {
                    // Handle exceptions, e.g., file not found, JSON parsing error
                    e.printStackTrace()
                }
            }
    }

    private fun saveDungeons() {

        val jsonString = Gson().toJson(dungeons)
        context.openFileOutput(dungeonsPathName, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }

    }

}