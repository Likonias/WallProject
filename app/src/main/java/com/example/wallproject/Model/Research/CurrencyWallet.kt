package com.example.wallproject.Model.Research

class CurrencyWallet(gold : Int, silver : Int, bronze : Int) {

    var currencies: MutableMap<Currency, Int> = mutableMapOf()

    init {

        currencies.set(Currency.GOLD, gold)
        currencies.set(Currency.SILVER, silver)
        currencies.set(Currency.BRONZE, bronze)

    }

    fun deposit(gold : Int, silver : Int, bronze : Int){

        currencies.set(Currency.GOLD, gold + currencies.getValue(Currency.GOLD))
        currencies.set(Currency.SILVER, silver + currencies.getValue(Currency.SILVER))
        currencies.set(Currency.BRONZE, bronze + currencies.getValue(Currency.BRONZE))

    }

    fun buy(cost : CurrencyWallet){

    }

}