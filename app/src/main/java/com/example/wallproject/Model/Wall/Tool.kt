package com.example.wallproject.Model.Wall

import com.example.wallproject.Model.Research.Currency
import com.example.wallproject.Model.Research.CurrencyEnum

data class Tool(var name : String, var miningPower : Double, var timeToMine : Double, var cost : Double, var upgradeCost : Currency){

    private val zero = 0
    private val upgradePowerIndex = 0.1
    private val upgradeTimeIndex = 0.95
    private val upgradeCostIndex = 2.5
    private val addIndex = 5.0

    var level : Int = zero
    var count : Int = zero

    var baseMiningPower : Double = miningPower
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

        upgradeCurrencyCost()

    }

    private fun upgradeCurrencyCost(){

        upgradeCost.gold = (upgradeCost.gold * upgradeCostIndex).toInt()
        upgradeCost.silver = (upgradeCost.silver * upgradeCostIndex).toInt()
        upgradeCost.bronze = (upgradeCost.bronze * upgradeCostIndex).toInt()

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
