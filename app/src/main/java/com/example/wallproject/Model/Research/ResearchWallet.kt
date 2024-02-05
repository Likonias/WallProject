package com.example.wallproject.Model.Research

class ResearchWallet {
    private val balances = mutableMapOf<Currency, Int>()

    init {
        Currency.values().forEach { currency ->
            balances[currency] = 0
        }
    }

    fun deposit(currency: Currency, amount: Int) {
        balances[currency] = balances.getOrDefault(currency, 0) + amount
    }

    fun withdraw(currency: Currency, amount: Int): Boolean {
        val currentBalance = balances.getOrDefault(currency, 0)
        if (currentBalance >= amount) {
            balances[currency] = currentBalance - amount
            return true
        }
        return false
    }

    fun getBalance(currency: Currency): Int {
        return balances.getOrDefault(currency, 0)
    }
}