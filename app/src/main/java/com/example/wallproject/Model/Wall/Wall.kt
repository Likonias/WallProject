package com.example.wallproject.Model.Wall

import kotlin.math.round

class Wall() {

    private val healthMultiplier = 1.2
    private var healthBase = 1000.0

    var health : Double = healthBase
    var shells : Int = 100
    var gameOver = false

    fun tickWall(stonesPerSecond : Double) : Int {
        health -= stonesPerSecond

        if(health <= 0){
            breakShell()
        }

        return health.toInt()
    }
    fun getCurrentHealth() : Double{
        return String.format("%.2f", health).toDouble()
    }
    private fun breakShell(){
        health = healthUpdate()

        if(shells == 0){
            gameOver = true
        }
        shells--
    }
    private fun healthUpdate() : Double {
        healthBase = healthBase * healthMultiplier
        return healthBase
    }
}