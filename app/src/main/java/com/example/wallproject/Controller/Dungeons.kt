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
import java.io.InputStream

class Dungeons(@Transient private var context: Context) {

    private val dungeonsPathName = "dungeons.json"

    private var dungeons : MutableList<Dungeon> = mutableListOf()

    @Transient private val db = Firebase.firestore
    @Transient private val docRef = db.collection("base").document("dungeons")

    var player : Player = Player(null)
    lateinit var currentEnemy : Enemy

    lateinit var currentDungeon : Dungeon

    init {
        //todo make sure to fix this
        //loadDungeons()
        var enemies : MutableList<Enemy> = mutableListOf()
        enemies.add(Enemy(0, "Joe", 100.0, Attack(10, 20, 1), Defense(5, 5) ))
        enemies.add(Enemy(1, "Stevie", 280.0, Attack(25, 10, 1), Defense(10, 5) ))
        enemies.add(Enemy(2, "Lucky Sword Jimmy", 600.0, Attack(20, 20, 100), Defense(10, 10) ))
        enemies.add(Enemy(3, "Right Mythos Handler", 1800.0, Attack(50, 5, 10), Defense(10, 15) ))
        enemies.add(Enemy(4, "King Pirate", 2999.0, Attack(90, 45, 28), Defense(40, 60) ))
        dungeons.add(Dungeon(0, enemies, "Suspicious Dungeon", "Every shaddow inside of the mysterious hole speaks in some weird language, there seems to be something truly suspicious about it."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 2000.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(1, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(2, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(3, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(4, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        dungeons.add(Dungeon(1, enemies, "Explosive Dungeon", "Be careful not to trip on the amount of all the dangerous things hidden inside."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(1, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(2, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(3, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(4, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        dungeons.add(Dungeon(2, enemies, "Undead Dungeon", "You can smell the rotting flesh emitting from the inside. It is truly disguising."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(1, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(2, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(3, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(4, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        dungeons.add(Dungeon(3, enemies, "Hell???", "It doesn't feel right. This feels wrong.."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(1, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(2, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(3, "Zombie Strong", 280.0, Attack(1, 1, 1), Defense(10, 15) ))
        enemies.add(Enemy(4, "Zombie", 100.0, Attack(1, 1, 1), Defense(10, 15) ))
        dungeons.add(Dungeon(4, enemies, "Truly Scary Dungeoooooon", "Beware of the spooks lurking inside. They may not be scary, but they will still be able to beat your sorry self."))

        //        saveDungeons()
    }

    fun initializePlayer(playerName : String){
        player.name = playerName
    }

    fun selectCurrentDungeon(dungeonId : Int) {

        currentDungeon = dungeons.get(dungeonId)

        currentEnemy = dungeons.get(dungeonId).getCurrentEnemy()!!

    }

    internal fun playerAttack() : Double {

        var healthAfterAttack = player.attack(currentEnemy)

        if (currentEnemy.isDead()){
            player.resetHealth()
            healthAfterAttack = 0.0
        }


        return healthAfterAttack

    }

    internal fun enemyAttack() : Double {

        var healthAfterAttack = currentEnemy.attack(player)

        if(player.isDead()) {
            player.resetHealth()
            currentEnemy.resetHealth()
            healthAfterAttack = 0.0
        }

        return healthAfterAttack

    }

    fun discoverNext() {

        var index = 0
        while (index < dungeons.size) {
            if (!dungeons[index].isDiscovered) {
                dungeons[index].isDiscovered = true
                break
            } else {
                index++
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

    fun getDiscoveredDungeons() : MutableList<Dungeon> {

        var discoveredDungeons : MutableList<Dungeon> = mutableListOf()

        for (dungeon in dungeons) {
            if(dungeon.isDiscovered){
                discoveredDungeons.add(dungeon)
            }
        }

        return discoveredDungeons

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