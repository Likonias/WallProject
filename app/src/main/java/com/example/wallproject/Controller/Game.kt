package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Account
import com.example.wallproject.Model.Dungeon.Dungeon
import com.example.wallproject.Model.Research.Currency
import com.example.wallproject.Model.Research.CurrencyWallet
import com.example.wallproject.Model.Research.Research
import com.example.wallproject.Model.Wall.Tool
import com.example.wallproject.Model.Wallet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class Game (context : Context){

    var account = Account()

    var tools = Tools(context)
    var dungeons = Dungeons(context)
    var research = Research()

    var wallet = Wallet()
    var curencyWallet = CurrencyWallet()

    init {

        //var toolssss = tools.

    }

    fun loadGame(){

    }

    fun saveGame(){

    }






}