package com.example.wallproject.Model.Wall

import kotlin.math.min

data class Tool(var name : String, var miningPower : Double, var timeToMine : Double, var cost : Double){

    private val zero = 0.0
    private val upgradePowerIndex = 1.05
    private val upgradeTimeIndex = 0.95
    private val upgradeCostIndex = 1.5
    private val addIndex = 2.0

    var level : Int = zero.toInt()
    var count : Int = zero.toInt()

    var baseMiningPower : Double = miningPower
    var upgradeCost : Double = cost * upgradeCostIndex
    var addCost : Double = cost * addIndex

    fun addTool(){

        count++

        addCost = addCost * addIndex

    }

    fun upgrade(){

        if(level % 10 == 0)
            upgradeAll()
        else if (level % 2 == 0)
            upgradeTime()
        else
            upgradePower()

        level++

        upgradeCost = upgradeCost * upgradeCostIndex

    }

    private fun upgradeTime(){

        timeToMine = timeToMine * upgradeTimeIndex

    }

    private fun upgradePower(){

        miningPower = miningPower + baseMiningPower * upgradePowerIndex

    }

    private fun upgradeAll(){

        upgradePower()

        upgradeTime()

    }

}
