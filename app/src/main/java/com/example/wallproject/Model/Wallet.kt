package com.example.wallproject.Model

data class Wallet(var balance : Double = 0.0) {

    fun buy(cost : Double) : Boolean {

        if(isEnoughBalance(cost))
            balance = balance - cost
            return true

        return false

    }

    fun deposit(amount: Double) : Double {

        balance = balance + amount

        return balance

    }

    fun getBalanceInt() : Int {

        return balance.toInt()

    }

    private fun isEnoughBalance(amount: Double) : Boolean {

        return amount < balance

    }

}