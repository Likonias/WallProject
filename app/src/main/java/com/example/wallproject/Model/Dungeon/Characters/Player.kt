package com.example.wallproject.Model.Dungeon.Characters

data class Player(
    override var name: String,
    override var health: Double,
    override var attack: Attack,
    override var defense: Defense
) : Character(){

    var level : Int = 0



}
