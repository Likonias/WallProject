package com.example.wallproject.View

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsAdd
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.databinding.ActivityToolsScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ToolsScreen : BaseActivity() {

    private lateinit var binding: ActivityToolsScreenBinding
    private lateinit var customAdapterToolsAdd: CustomAdapterToolsAdd
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityToolsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapterToolsAdd = CustomAdapterToolsAdd(GameSingleton.game.tools.getToolsDiscovered())

        // Set up RecyclerView with a LinearLayoutManager
        binding.recyclerViewAdd.layoutManager = LinearLayoutManager(this)

        // Attach the adapter to the RecyclerView
        binding.recyclerViewAdd.adapter = customAdapterToolsAdd



        startUpdatingData()

    }

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.stonesTextView.text = GameSingleton.game.wallet.getBalanceFormated().toString()

                // Delay for 0,1 second for smoother experience
                delay(100)

            }
        }

    }

}