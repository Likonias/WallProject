package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsAdd
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.databinding.ActivityToolsScreenBinding

class ToolsScreen : AppCompatActivity() {

    private lateinit var binding: ActivityToolsScreenBinding
    private lateinit var customAdapterToolsAdd: CustomAdapterToolsAdd
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityToolsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapterToolsAdd = CustomAdapterToolsAdd(GameSingleton.game.tools.getToolsDiscovered())

        // Set up RecyclerView with a LinearLayoutManager
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Attach the adapter to the RecyclerView
        binding.recyclerView.adapter = customAdapterToolsAdd

    }
}