package com.example.wallproject.Model.Wallets

class Wallet() {

    var balance : Double = 1.0

    fun buy(cost : Double) : Boolean {

        if(isEnoughBalance(cost)){
            balance = balance - cost
            return true
        }

        return false

    }

    fun deposit(amount: Double) : Double {

        balance = balance + amount

        return balance

    }

    fun getBalanceFormated() : String {

        return String.format("%.2f", balance).toDouble().toString()

    }

    fun isEnoughBalance(amount: Double) : Boolean {

        return amount <= balance

    }

}