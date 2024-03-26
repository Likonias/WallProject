package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsAdd
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsResearch
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityResearchScreenBinding
import com.example.wallproject.databinding.ActivityToolsScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResearchScreen : AppCompatActivity() {

    private lateinit var binding: ActivityResearchScreenBinding
    private lateinit var customAdapterToolsResearch: CustomAdapterToolsResearch
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityResearchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapterToolsResearch = CustomAdapterToolsResearch(GameSingleton.game.tools.getToolsDiscovered())

        // Set up RecyclerView with a LinearLayoutManager
        binding.recyclerViewResearch.layoutManager = LinearLayoutManager(this)

        // Attach the adapter to the RecyclerView
        binding.recyclerViewResearch.adapter = customAdapterToolsResearch

        startUpdatingData()
    }

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.stonesResearchTextView.text = GameSingleton.game.wallet.getBalanceInt().toString()

                binding.currentBronzeTextView.text = GameSingleton.game.currencyWallet.getBronze().toString()
                binding.currentSilverTextView.text = GameSingleton.game.currencyWallet.getSilver().toString()
                binding.currentGoldTextView.text = GameSingleton.game.currencyWallet.getGold().toString()

                // Delay for 0,1 second for smoother experience
                delay(100)

            }
        }

    }

}