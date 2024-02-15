package com.example.wallproject.Model.Research

class CurrencyWallet() {

    val zero : Int = 0

    var currencies: MutableMap<CurrencyEnum, Int> = mutableMapOf()


    init {

        currencies.set(CurrencyEnum.GOLD, zero)
        currencies.set(CurrencyEnum.SILVER, zero)
        currencies.set(CurrencyEnum.BRONZE, zero)

    }

    fun deposit(currency: Currency){

        currencies.set(CurrencyEnum.GOLD, currency.gold + currencies.getValue(CurrencyEnum.GOLD))
        currencies.set(CurrencyEnum.SILVER, currency.silver + currencies.getValue(CurrencyEnum.SILVER))
        currencies.set(CurrencyEnum.BRONZE, currency.bronze + currencies.getValue(CurrencyEnum.BRONZE))

    }

    fun buy(cost: Currency): Boolean {

        if (canAfford(cost)) {

            currencies[CurrencyEnum.GOLD] = currencies.getValue(CurrencyEnum.GOLD) - cost.gold
            currencies[CurrencyEnum.SILVER] = currencies.getValue(CurrencyEnum.SILVER) - cost.silver
            currencies[CurrencyEnum.BRONZE] = currencies.getValue(CurrencyEnum.BRONZE) - cost.bronze

            return true

        }

        return false

    }

    private fun canAfford(cost: Currency): Boolean {
        val currentGold = currencies.getValue(CurrencyEnum.GOLD)
        val currentSilver = currencies.getValue(CurrencyEnum.SILVER)
        val currentBronze = currencies.getValue(CurrencyEnum.BRONZE)

        return currentGold >= cost.gold && currentSilver >= cost.silver && currentBronze >= cost.bronze
    }

}