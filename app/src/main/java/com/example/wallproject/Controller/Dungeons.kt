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

    var dungeonsTimerSeconds = 0

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
        enemies.add(Enemy(0, "Zombie", 2000.0, Attack(60, 10, 50), Defense(20, 70) ))
        enemies.add(Enemy(1, "Zombie Strong", 3000.0, Attack(80, 40, 2), Defense(40, 40) ))
        enemies.add(Enemy(2, "Zombie", 8000.0, Attack(10, 10, 999), Defense(999, 999) ))
        enemies.add(Enemy(3, "Zombie Strong", 4000.0, Attack(50, 90, 50), Defense(25, 60) ))
        enemies.add(Enemy(4, "Zombie", 5600.0, Attack(100, 100, 100), Defense(100, 100) ))
        dungeons.add(Dungeon(1, enemies, "Explosive Dungeon", "Be careful not to trip on the amount of all the dangerous things hidden inside."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 2500.0, Attack(90, 90, 70), Defense(50, 50) ))
        enemies.add(Enemy(1, "Zombie Strong", 6000.0, Attack(45, 100, 5), Defense(20, 20) ))
        enemies.add(Enemy(2, "Zombie", 8900.0, Attack(80, 80, 240), Defense(80, 80) ))
        enemies.add(Enemy(3, "Zombie Strong", 7000.0, Attack(75, 150, 15), Defense(20, 40) ))
        enemies.add(Enemy(4, "Zombie", 10000.0, Attack(160, 5, 20), Defense(20, 20) ))
        dungeons.add(Dungeon(2, enemies, "Undead Dungeon", "You can smell the rotting flesh emitting from the inside. It is truly disguising."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 6969.0, Attack(69, 69, 69), Defense(69, 69) ))
        enemies.add(Enemy(1, "Zombie Strong", 4000.0, Attack(90, 90, 90), Defense(66, 77) ))
        enemies.add(Enemy(2, "Zombie", 14500.0, Attack(120, 90, 1), Defense(50, 40) ))
        enemies.add(Enemy(3, "Zombie Strong", 19000.0, Attack(40, 200, 70), Defense(10, 50) ))
        enemies.add(Enemy(4, "Zombie", 25000.0, Attack(90, 280, 20), Defense(40, 70) ))
        dungeons.add(Dungeon(3, enemies, "Hell???", "It doesn't feel right. This feels wrong.."))
        enemies = mutableListOf()
        enemies.add(Enemy(0, "Zombie", 20000.0, Attack(70, 130, 70), Defense(50, 120) ))
        enemies.add(Enemy(1, "Zombie Strong", 28000.0, Attack(160, 115, 50), Defense(70, 80) ))
        enemies.add(Enemy(2, "Zombie", 30000.0, Attack(200, 230, 20), Defense(40, 200) ))
        enemies.add(Enemy(3, "Zombie Strong", 45000.0, Attack(450, 100, 45), Defense(200, 400) ))
        enemies.add(Enemy(4, "Zombie", 66666.0, Attack(666, 666, 666), Defense(666, 666) ))
        dungeons.add(Dungeon(4, enemies, "Truly Scary Dungeoooooon", "Beware of the spooks lurking inside. They may not be scary, but they will still be able to beat your sorry self."))

        //        saveDungeons()
    }

    fun attackTimerZero() : Boolean {
        return dungeonsTimerSeconds == 0
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
            dungeonsTimerSeconds = 600
        }


        return healthAfterAttack

    }

    internal fun enemyAttack() : Double {

        var healthAfterAttack = currentEnemy.attack(player)

        if(player.isDead()) {
            player.resetHealth()
            currentEnemy.resetHealth()
            healthAfterAttack = 0.0
            dungeonsTimerSeconds = 600
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