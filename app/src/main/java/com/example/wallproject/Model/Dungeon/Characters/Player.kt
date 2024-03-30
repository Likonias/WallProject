package com.example.wallproject.Model.Dungeon.Characters

data class Player(
    override var name: String?,
    override var health: Double = 100.0,
    override var attack: Attack = Attack(1, 1, 1),
    override var defense: Defense = Defense(1, 1)
) : Character(){

    @Transient private val healthMultiplier = 0.05
    @Transient private val levelUpPoints = 3
    @Transient private val baseLevelUpCost = 1000
    @Transient private val levelUpCostMultiplier = 5

    init {
        currentHealth = health
    }

    private var baseHealth = health

    var level : Int = 1

    var levelUpCost : Int = baseLevelUpCost

    var pointsToSpend = 0

    fun levelUp(){

        if (level % 10 == 0 ) {
            updateBaseHealth()
        }

        updateHealth()

        pointsToSpend = pointsToSpend + levelUpPoints

        levelUpCost = levelUpCost * levelUpCostMultiplier

        level++

    }

    private fun updateBaseHealth() {

        baseHealth = health

    }

    fun canUpdate() : Boolean {
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

    fun usePoint() {

        if (canUpdate()) {
            pointsToSpend--
        }

    }

}
