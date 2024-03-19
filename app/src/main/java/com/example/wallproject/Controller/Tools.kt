package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Wallets.Currency
import com.example.wallproject.Model.Wall.Tool
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Tools (@Transient private var context: Context) {

    private val toolsPathName = "tools.json"

    private var tools : MutableList<Tool> = mutableListOf()

    @Transient private val db = Firebase.firestore
    @Transient private val docRef = db.collection("base").document("tools")

    init {
        //loadTools()

        tools.add(Tool(0, "Pickaxe", 1.0, 10.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(1, "Stone Saw", 1.5, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(2, "Jackhammer", 50.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(3, "Automatic Saw", 25.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(4, "Stone Destoryer", 50.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(5, "Crystal Pickaxe", 25.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(6, "Mystic Hammer", 50.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(7, "Mage", 25.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(8, "Magical Void", 50.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        tools.add(Tool(9, "Stone Annihilator", 25.0, 5.0, 100.0, Currency(0, 10, 20), 5000))

        //saveTools()
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

        tools.forEach { tool ->
            if(tool.id == toolId)
                tool.upgrade()
        }

    }

    fun add (toolId: Int) {

        tools.forEach { tool ->
            if(tool.id == toolId)
                tool.addTool()
        }

    }

    fun discover (toolId: Int) {

        tools.forEach { tool ->
            if(tool.id == toolId)
                tool.isDiscovered = true
        }

    }

    fun research (toolId: Int) {

        tools.forEach { tool ->
            if(tool.id == toolId)
                tool.isResearched = true
        }

    }

    fun getSPS() : Double {

        return getMiningPower() / getTime()

    }

    fun getTools() : MutableList<Tool> {

        return tools

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