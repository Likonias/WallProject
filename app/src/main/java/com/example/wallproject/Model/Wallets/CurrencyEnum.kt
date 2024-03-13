package com.example.wallproject.Model.Wallets

enum class CurrencyEnum (val value : Int, val chance : Double) {

    BRONZE(1000, 0.1),
    SILVER(15000, 0.01),
    GOLD(300000, 0.001)

}