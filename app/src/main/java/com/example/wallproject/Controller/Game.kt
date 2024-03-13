package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Account
import com.example.wallproject.Model.Wallets.CurrencyWallet
import com.example.wallproject.Model.Wall.Wall
import com.example.wallproject.Model.Wallets.Wallet
import kotlinx.coroutines.*

class Game (context : Context){

    var account = Account()

    var tools = Tools(context)
    var dungeons = Dungeons(context)

    var wall = Wall { checkIfDungeonIsDiscovered() }

    var wallet = Wallet()
    var currencyWallet = CurrencyWallet()

    //5 first items can be
    @Transient private val itemsForDiscoveryValue = 5
    @Transient private var discoveredDungeonId = 0
    @Transient private var currentDungeonId : Int? = null

    val backgroudWallTick = GlobalScope.launch {

        while (true) {

            delay(1000)

            var sps = tools.getSPS()

            wall.tickWall(sps)

            wallet.deposit(sps)

        }

    }

    fun addTool(toolId : Int) {

        val toolCost = tools.getTool(toolId).cost

        if (wallet.isEnoughBalance(toolCost)) {

            wallet.buy(toolCost)

            tools.add(toolId)

        }

    }

    fun researchTool(toolId: Int) {

        val toolCost = tools.getTool(toolId).researchCost.toDouble()

        if (wallet.isEnoughBalance(toolCost)) {

            wallet.buy(toolCost)

            tools.research(toolId)

        }

    }

    fun dungeonPlayerAttack() : Double {
        var healthAfterAttack = dungeons.playerAttack()

        if(healthAfterAttack <= 0){
            discoverTool(dungeons.currentDungeon)
        }

        return healthAfterAttack

    }

    fun dungeonEnemyAttack() : Double {

        return dungeons.enemyAttack()

    }

    private fun discoverTool(dungeonId : Int) {

        tools.discover(dungeonId + itemsForDiscoveryValue)

    }

    fun upgradeTool(toolId: Int) {

        var toolCost = tools.getTool(toolId).upgradeCost

        if(currencyWallet.isEnoughBalance(toolCost)){

            currencyWallet.buy(toolCost)

            tools.upgrade(toolId)

        }

    }

    private fun checkIfDungeonIsDiscovered() {

        if(wall.shells <= 50 && wall.shells % 10 == 0){

            dungeons.discover(discoveredDungeonId)

            discoveredDungeonId++

        }

    }

    fun loadGame(){

    }

    fun saveGame(){

    }

}