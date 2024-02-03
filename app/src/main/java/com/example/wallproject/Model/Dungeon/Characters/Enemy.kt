package com.example.wallproject.Model.Dungeon.Characters

data class Enemy(val id : Int,
                 override var name: String,
                 override var health: Double,
                 override var attack: Attack,
                 override var defense: Defense
) : Character(){



}
