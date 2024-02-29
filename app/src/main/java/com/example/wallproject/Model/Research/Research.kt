package com.example.wallproject.Model.Research

import com.example.wallproject.Model.Wall.Tool

data class Research(var tools : MutableList<Tool>){

    fun researchTool(id : Int){

        val tool = tools.find { it.id == id }

        if (tool != null) {

            tool.isResearched = true

        }

    }

    fun updateTool(id : Int){

        val tool = tools.find { it.id == id }

        if (tool != null) {

            tool.upgrade()

        }

    }

}
