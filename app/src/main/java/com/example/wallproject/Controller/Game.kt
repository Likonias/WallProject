package com.example.wallproject.Controller

import android.content.Context
import com.example.wallproject.Model.Account
import com.example.wallproject.Model.Dungeon.Dungeon
import com.example.wallproject.Model.Research.Currency
import com.example.wallproject.Model.Research.Research
import com.example.wallproject.Model.Wall.Tool
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class Game (context : Context){

    var tools : MutableList<Tool> = mutableListOf()
    var dungeons = Dungeons(context)
    var research = Research()


    init {
        tools.add(Tool(0, "Hellbreaker", 50.0, 5.0, 100.0, Currency(0, 10, 20), 5000))
        println(tools.get(0))
        research.researchTool(tools.get(0))
        println(tools.get(0))
    }

    private fun loadTools(){

    }

    private fun saveTools(){

    }




}