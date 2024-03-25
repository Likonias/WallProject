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
//todo finish tools
        tools.add(Tool(0, "Pickaxe", 1.0, 10.0, 1.0, Currency(0, 0, 10), 10))
        tools.add(Tool(1, "Stone Saw", 1.5, 15.0, 100.0, Currency(0, 0, 100), 5000))
        tools.add(Tool(2, "Jackhammer", 5.0, 30.0, 100.0, Currency(0, 0, 500), 5000))
        tools.add(Tool(3, "Automatic Saw", 15.0, 60.0, 100.0, Currency(0, 1, 500), 5000))
        tools.add(Tool(4, "Stone Destoryer", 30.0, 90.0, 100.0, Currency(0, 10, 1000), 5000))
        tools.add(Tool(5, "Crystal Pickaxe", 60.0, 120.0, 100.0, Currency(0, 50, 1000), 5000))
        tools.add(Tool(6, "Mystic Hammer", 120.0, 140.0, 100.0, Currency(0, 150, 1500), 5000))
        tools.add(Tool(7, "Mage", 160.0, 170.0, 100.0, Currency(0, 300, 2000), 5000))
        tools.add(Tool(8, "Magical Void", 200.0, 200.0, 100.0, Currency(1, 500, 2500), 5000))
        tools.add(Tool(9, "Stone Annihilator", 1000.0, 500.0, 100.0, Currency(1000, 1000, 1000), 5000))
        tools.get(0).isDiscovered = true
        tools.get(1).isDiscovered = true
        tools.get(2).isDiscovered = true
        tools.get(3).isDiscovered = true
        tools.get(4).isDiscovered = true
        tools.get(5).isDiscovered = true
        tools.get(6).isDiscovered = true
        tools.get(7).isDiscovered = true
        tools.get(8).isDiscovered = true
        tools.get(9).isDiscovered = true
        //tools.get(0).addTool()
        tools.get(1).addTool()
        tools.get(2).addTool()
        tools.get(3).addTool()
        tools.get(4).addTool()
        tools.get(5).addTool()
        tools.get(6).addTool()
        tools.get(7).addTool()
        tools.get(8).addTool()
        tools.get(9).addTool()

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

        val roundedNumber = String.format("%.2f", getMiningPower() / getTime()).toDouble()

        return roundedNumber

    }

    fun getActiveToolsCount() : Int {

        var count = 0

        for (tool in tools){
            if(tool.count > 0){
                count++
            }
        }

        return count
    }

    fun getTools() : MutableList<Tool> {

        return tools

    }

    fun getToolsDiscovered() : MutableList<Tool> {

        var discoveredTools : MutableList<Tool> = mutableListOf()

        for (tool in tools){
            if(tool.isDiscovered){
                discoveredTools.add(tool)
            }
        }

        return discoveredTools

    }

    private fun getMiningPower() : Double {

        var miningPower = 0.0

        tools.forEach {
            tool -> miningPower += tool.miningPower * tool.count
        }

        return miningPower

    }

    private fun getTime() : Double {

        var time = 0.0

        tools.forEach { tool ->
            if(tool.count > 0){
                time += tool.timeToMine
            }
        }

        return time

    }

}