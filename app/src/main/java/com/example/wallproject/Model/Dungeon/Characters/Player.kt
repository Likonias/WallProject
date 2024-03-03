package com.example.wallproject.Model.Dungeon.Characters

data class Player(
    override var name: String,
    override var health: Double,
    override var attack: Attack,
    override var defense: Defense
) : Character(){

    //todo add level up costs

    @Transient private val healthMultiplier = 0.05
    @Transient private val levelUpPoints = 3

    private var baseHealth = health

    var level : Int = 1

    var pointsToSpend = 0

    fun levelUp(){

        if (level % 10 == 0 )
            updateBaseHealth()

        updateAD()
        updateHealth()

        pointsToSpend = pointsToSpend + levelUpPoints

        level++

    }

    private fun updateBaseHealth() {

        baseHealth = health

    }

    private fun canUpdate() : Boolean {
        return pointsToSpend != 0
    }

    fun updateHealth(){

        health = health + baseHealth * healthMultiplier

    }

    fun updateAD(){

        attack.attackDmg++

    }

    fun updateAP(){

        attack.abilityPower++

    }

    fun updateArmor(){

        defense.armor++

    }

    fun updateMagicResist(){

        defense.magicResist++

    }

    fun updateLuck(){

        attack.luck++

    }

}
