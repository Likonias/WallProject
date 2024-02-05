package com.example.wallproject.Model.Wall

class Wall {

    private val healthMultiplier = 2.5
    private var healthBase = 1000000.0

    var health : Double = healthBase
    var shells : Int = 100

    fun breakShell(){

        health = healthUpdate()

        shells--

    }

    private fun healthUpdate() : Double {

        healthBase = healthBase * healthMultiplier

        return healthBase

    }

}