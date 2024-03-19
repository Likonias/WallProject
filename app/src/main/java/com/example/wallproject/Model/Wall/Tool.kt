package com.example.wallproject.Model.Wall

import com.example.wallproject.Model.Wallets.Currency

data class Tool(val id : Int, var name : String, var miningPower : Double, var timeToMine : Double, var cost : Double, var upgradeCost : Currency, val researchCost : Int){

    //transient is exlucing values of being serialized by the GSON class for JSON
    @Transient private val zero = 0
    @Transient private val upgradePowerIndex = 0.1
    @Transient private val upgradeTimeIndex = 0.95
    @Transient private val upgradeCostIndex = 2.5
    @Transient private val addIndex = 5.0

    var level : Int = zero
    var count : Int = zero

    var isDiscovered : Boolean = false
    var isResearched : Boolean = false

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

        if(upgradeCost.silver == 0 && upgradeCost.bronze > 1000){
            upgradeCost.silver = 1
        }

        if(upgradeCost.gold == 0 && upgradeCost.silver > 1000){
            upgradeCost.gold = 1
        }

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
