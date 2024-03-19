package com.example.wallproject.Model.Wallets

import kotlin.random.Random

class CurrencyWallet() {

    @Transient val zero : Int = 0

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

        if (isEnoughBalance(cost)) {

            currencies[CurrencyEnum.GOLD] = currencies.getValue(CurrencyEnum.GOLD) - cost.gold
            currencies[CurrencyEnum.SILVER] = currencies.getValue(CurrencyEnum.SILVER) - cost.silver
            currencies[CurrencyEnum.BRONZE] = currencies.getValue(CurrencyEnum.BRONZE) - cost.bronze

            return true

        }

        return false

    }

    fun searchThroughStone(stones : Int) {

        var foundCurrencies = Currency(0,0,0)

        repeat(stones) {
            val randomNumber = Random.nextDouble(0.0, 100.0)

            if (randomNumber <= CurrencyEnum.GOLD.chance) {
                foundCurrencies.gold += 1
            } else if (randomNumber <= CurrencyEnum.SILVER.chance) {
                foundCurrencies.silver += 1
            }else if (randomNumber <= CurrencyEnum.BRONZE.chance) {
                foundCurrencies.bronze += 1
            }

        }

        deposit(foundCurrencies)

    }

    fun isEnoughBalance(cost: Currency): Boolean {
        val currentGold = currencies.getValue(CurrencyEnum.GOLD)
        val currentSilver = currencies.getValue(CurrencyEnum.SILVER)
        val currentBronze = currencies.getValue(CurrencyEnum.BRONZE)

        return currentGold >= cost.gold && currentSilver >= cost.silver && currentBronze >= cost.bronze
    }

}