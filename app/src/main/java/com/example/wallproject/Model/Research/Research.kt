package com.example.wallproject.Model.Research

import com.example.wallproject.Model.Wall.Tool

class Research(){
    fun researchTool(tool: Tool){

        if (tool != null)
            tool.isResearched = true

    }

    fun updateTool(tool: Tool){

        if (tool != null)
            tool.upgrade()

    }
}
