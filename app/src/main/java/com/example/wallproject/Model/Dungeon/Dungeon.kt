package com.example.wallproject.Model.Dungeon

import com.example.wallproject.Model.Dungeon.Characters.Enemy

data class Dungeon(val id : Int, var enemies : MutableList<Enemy>, var name : String, var description : String) {

    var isDiscovered : Boolean = false

    fun getCurrentEnemy() : Enemy? {

        enemies.forEach { enemy ->

            if(!enemy.isDead()){
                return enemy
            }

        }

        return null

    }

}