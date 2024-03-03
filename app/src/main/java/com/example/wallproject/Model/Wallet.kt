package com.example.wallproject.Model

data class Wallet(var balance : Double = 0.0) {

    //todo maybe redo this wallet

    fun withdraw(amount : Double) : Double {

        if(isEnoughBalance(amount))
            balance = balance - amount

        return balance

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