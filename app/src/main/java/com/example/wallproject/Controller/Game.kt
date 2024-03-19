package com.example.wallproject.Controller

import android.content.Context
import android.util.Log
import com.example.wallproject.Model.Account
import com.example.wallproject.Model.Wallets.CurrencyWallet
import com.example.wallproject.Model.Wall.Wall
import com.example.wallproject.Model.Wallets.Currency
import com.example.wallproject.Model.Wallets.CurrencyEnum
import com.example.wallproject.Model.Wallets.Wallet
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Game (private var context : Context){

    var account = Account()

    var tools = Tools(context)
    var dungeons = Dungeons(context)

    var wall = Wall { checkIfDungeonIsDiscovered() }

    var wallet = Wallet()
    var currencyWallet = CurrencyWallet()

    //5 first items can be
    private val itemsForDiscoveryValue = 5
    private var currentDungeonId : Int? = null

    private val db = Firebase.firestore
    private val docRef = db.collection("userData")

    val backgroudWallTick = GlobalScope.launch {

        while (true) {

            delay(1000)

            var sps = tools.getSPS()

            wall.tickWall(sps)

            wallet.deposit(sps)

        }

    }
    //todo init for saving data, locally and from firestore
    init {
        loadGame()
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

    fun searchThroughStones(stones : Int) : Boolean{
        if(wallet.isEnoughBalance(stones.toDouble())){

            wallet.buy(stones.toDouble())

            currencyWallet.searchThroughStone(stones)

            return true
        }
        return false
    }

    fun buyBronze(count : Int) : Boolean {

        var fullCost = count.toDouble() * CurrencyEnum.BRONZE.value

        if(wallet.isEnoughBalance(fullCost)){

            wallet.buy(fullCost)

            currencyWallet.buy(Currency(0, 0, count))

            return true

        }

        return false

    }

    fun buySilver(count: Int) : Boolean {

        var fullCost = count.toDouble() * CurrencyEnum.SILVER.value

        if(wallet.isEnoughBalance(fullCost)){

            wallet.buy(fullCost)

            currencyWallet.buy(Currency(0,  count, 0))

            return true

        }

        return false

    }

    fun buyGold(count: Int) : Boolean {

        var fullCost = count.toDouble() * CurrencyEnum.GOLD.value

        if(wallet.isEnoughBalance(fullCost)){

            wallet.buy(fullCost)

            currencyWallet.buy(Currency(count, 0, 0))

            return true

        }

        return false

    }

    private fun checkIfDungeonIsDiscovered() {

        if(wall.shells <= 50 && wall.shells % 10 == 0){

            dungeons.discoverNext()

        }

    }

    fun setGoogleId(googleId : String?) {
        account.googleId = googleId
    }

    fun loadGame(){

        if(account.googleId != null){

            // Fetch data from Firestore
            docRef.document(account.googleId.toString()).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val accountJson = document.getString("account")
                        val toolsJson = document.getString("tools")
                        val dungeonsJson = document.getString("dungeons")
                        val wallJson = document.getString("wall")
                        val walletJson = document.getString("wallet")
                        val currencyWalletJson = document.getString("currencyWallet")

                        val gson = Gson()
                        account = gson.fromJson(accountJson, Account::class.java)
                        tools = gson.fromJson(toolsJson, Tools::class.java)
                        dungeons = gson.fromJson(dungeonsJson, Dungeons::class.java)
                        wall = gson.fromJson(wallJson, Wall::class.java)
                        wallet = gson.fromJson(walletJson, Wallet::class.java)
                        currencyWallet = gson.fromJson(currencyWalletJson, CurrencyWallet::class.java)
                    } else {
                        Log.e("Firestore", "No document found for user: ${account.googleId}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error loading game data from Firestore: ${e.message}", e)
                }

        } else {

            try {
                //load local data
                val inputStream = context.openFileInput("game_data.json")
                val reader = BufferedReader(InputStreamReader(inputStream))
                val accountJson = reader.readLine()
                val toolsJson = reader.readLine()
                val dungeonsJson = reader.readLine()
                val wallJson = reader.readLine()
                val walletJson = reader.readLine()
                val currencyWalletJson = reader.readLine()

                // Parse JSON strings back to objects
                val gson = Gson()
                account = gson.fromJson(accountJson, Account::class.java)
                tools = gson.fromJson(toolsJson, Tools::class.java)
                dungeons = gson.fromJson(dungeonsJson, Dungeons::class.java)
                wall = gson.fromJson(wallJson, Wall::class.java)
                wallet = gson.fromJson(walletJson, Wallet::class.java)
                currencyWallet = gson.fromJson(currencyWalletJson, CurrencyWallet::class.java)

            } catch (e: Exception) {
                Log.e("YourTag", "An error occurred: ${e.message}", e)
            }

        }
    }

    fun saveGame() {

        // Convert game data to JSON strings
        val accountJson = Gson().toJson(account)
        val toolsJson = Gson().toJson(tools)
        val dungeonsJson = Gson().toJson(dungeons)
        val wallJson = Gson().toJson(wall)
        val walletJson = Gson().toJson(wallet)
        val currencyWalletJson = Gson().toJson(currencyWallet)

        if(account.googleId != null){

            // Save to Firestore
            val data = hashMapOf(
                "account" to accountJson,
                "tools" to toolsJson,
                "dungeons" to dungeonsJson,
                "wall" to wallJson,
                "wallet" to walletJson,
                "currencyWallet" to currencyWalletJson
            )

            docRef.document(account.googleId.toString()).set(data)
                .addOnSuccessListener {
                    println("Game data saved to Firestore successfully.")
                }
                .addOnFailureListener { e ->
                    println("Error saving game data to Firestore: $e")
                }

        }

        try {

            // Save locally
            val outputStream = context.openFileOutput("game_data.json", Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(outputStream)
            writer.use {
                it.write(accountJson + "\n")
                it.write(toolsJson + "\n")
                it.write(dungeonsJson + "\n")
                it.write(wallJson + "\n")
                it.write(walletJson + "\n")
                it.write(currencyWalletJson + "\n")
            }

        } catch (e: Exception) {
            Log.e("YourTag", "An error occurred: ${e.message}", e)
        }

    }

}