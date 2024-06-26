package com.example.wallproject.Model.Dungeon.Characters

data class Player(
    override var name: String?,
    override var health: Double = 100.0,
    override var attack: Attack = Attack(1, 1, 1),
    override var defense: Defense = Defense(1, 1)
) : Character(){

    private val healthMultiplier = 0.05
    private val levelUpPoints = 3
    private val levelUpCostMultiplier = 1.5
    private val addSimpleIndex = 0.4

    init {
        currentHealth = health
    }

    private var baseHealth = health
    private var baseLevelUpCost = 5000


    var level : Int = 1

    var levelUpCost : Int = baseLevelUpCost

    var pointsToSpend = 0

    fun levelUp(){

        if (level % 10 == 0 ) {
            updateBaseValues()
        }

        updateHealth()

        pointsToSpend = pointsToSpend + levelUpPoints

        levelUpCost = (levelUpCost + baseLevelUpCost * addSimpleIndex).toInt()

        level++

    }

    private fun updateBaseValues() {

        baseHealth = health

        baseLevelUpCost = levelUpCost

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
