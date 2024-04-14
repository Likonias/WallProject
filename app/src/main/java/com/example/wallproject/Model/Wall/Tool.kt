package com.example.wallproject.Model.Wall

import com.example.wallproject.Model.Wallets.Currency

data class Tool(val id : Int, var name : String, var miningPower : Double, var timeToMine : Double, var cost : Double, var upgradeCost : Currency, val researchCost : Int){

    //transient is exlucing values of being serialized by the GSON class for JSON
    private val zero = 0
    private val addingNextCurrencyTreshold = 1000
    private val upgradePowerIndex = 0.1
    private val upgradeTimeIndex = 0.95
    private val upgradeCostIndex = 2.5
    private val addIndex = 1.15
    private val addSimpleIndex = 0.4

    private var baseCost = cost

    var level : Int = zero
    var count : Int = zero

    var isDiscovered : Boolean = false
    var isResearched : Boolean = false

    var baseMiningPower : Double = miningPower

    fun addTool(){

        if(count % 2 == 0){
            baseCost = baseCost * addIndex
        }

        count++

        cost = cost + baseCost * addSimpleIndex

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
