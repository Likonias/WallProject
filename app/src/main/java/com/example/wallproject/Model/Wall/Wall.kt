package com.example.wallproject.Model.Wall

import kotlin.math.round

class Wall {

    //transient is exlucing values of being serialized by the GSON class for JSON
    @Transient private val healthMultiplier = 2.5
    @Transient private var healthBase = 1000000.0

    var health : Double = healthBase
    var shells : Int = 100

    fun tickWall(stonesPerSecond : Double) : Int {

        health - stonesPerSecond

        if(health <= 0)
            breakShell()

        return health.toInt()

    }

    private fun breakShell(){

        health = healthUpdate()

        shells--

    }

    private fun healthUpdate() : Double {

        healthBase = healthBase * healthMultiplier

        return healthBase

    }

}