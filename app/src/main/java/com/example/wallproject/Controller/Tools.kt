package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Dungeon.Dungeon
import com.example.wallproject.Model.Research.Currency
import com.example.wallproject.Model.Research.CurrencyWallet
import com.example.wallproject.Model.Wall.Tool
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Tools (context: Context) {

    private val toolsPathName = "tools.json"

    private var tools : MutableList<Tool> = mutableListOf()

    private val db = Firebase.firestore
    private val docRef = db.collection("base").document("tools")

    private var context = context

    init {
        loadTools()
        tools.add(Tool(0, "Hellbreaker", 50.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(0, "Pickaxe", 25.0, 5.0, 100.0, Currency(0, 10, 20), 5000))

        saveTools()
    }

    private fun loadTools() {
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists()) {
                    val jsonText = document.getString("json")
                    val type = object : TypeToken<List<Tool>>() {}.type
                    tools = Gson().fromJson(jsonText, type)
                }
                saveTools()
            } else {
                context.openFileInput(toolsPathName).use { inputStream ->
                    val jsonText = inputStream.bufferedReader().use { it.readText() }
                    val type = object : TypeToken<List<Tool>>() {}.type
                    tools = Gson().fromJson(jsonText, type)
                }
            }
        }
    }

    private fun saveTools() {

        val jsonString = Gson().toJson(tools)
        context.openFileOutput(toolsPathName, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }

    }

    fun getTool (toolId : Int) : Tool {
        return tools.get(toolId)
    }

    fun upgrade (toolId: Int) {



    }

    private fun getMiningPower() : Double {

        var miningPower = 0.0

        tools.forEach { tool -> miningPower += tool.miningPower }

        return miningPower

    }

    private fun getTime() : Double {

        var time = 0.0

        tools.forEach { tool -> time += tool.miningPower }

        return time

    }

}