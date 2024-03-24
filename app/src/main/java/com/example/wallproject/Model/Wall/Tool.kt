package com.example.wallproject.Model.Wall

import com.example.wallproject.Model.Wallets.Currency

data class Tool(val id : Int, var name : String, var miningPower : Double, var timeToMine : Double, var cost : Double, var upgradeCost : Currency, val researchCost : Int){

    //transient is exlucing values of being serialized by the GSON class for JSON
    @Transient private val zero = 0
    @Transient private val addingNextCurrencyTreshold = 1000
    @Transient private val upgradePowerIndex = 0.1
    @Transient private val upgradeTimeIndex = 0.95
    @Transient private val upgradeCostIndex = 2.5
    @Transient private val addIndex = 5.0

    var level : Int = zero
    var count : Int = zero

    var isDiscovered : Boolean = false
    var isResearched : Boolean = false

    var baseMiningPower : Double = miningPower

    fun addTool(){

        count++

        cost = cost * addIndex

    }

    fun upgrade(){

        if(level % 10 == zero)
            upgradeAll()
        else if (level % 2 == zero)
            upgradeTime()
        else
            upgradePower()

        level++

        upgradeCurrencyCost()

    }

    fun getTimeToMineFormated() : Double {
        return String.format("%.2f", timeToMine).toDouble()
    }

    fun getMiningPowerFormated() : Double {
        return String.format("%.2f", miningPower).toDouble()
    }

    fun isOwned() : Boolean {
        return count > zero
    }

    private fun upgradeCurrencyCost(){

        if(upgradeCost.silver == zero && upgradeCost.bronze > addingNextCurrencyTreshold){
            upgradeCost.silver = 1
        }

        if(upgradeCost.gold == zero && upgradeCost.silver > addingNextCurrencyTreshold){
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
