package com.example.wallproject.View

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallproject.Model.CustomAdapters.CustomAdapterToolsResearch
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.databinding.ActivityResearchScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResearchScreen : AppCompatActivity() {

    private lateinit var binding: ActivityResearchScreenBinding
    private lateinit var customAdapterToolsResearch: CustomAdapterToolsResearch

    private val bronzeValue = GameSingleton.game.currencyWallet.getBronzeValue()
    private val silverValue = GameSingleton.game.currencyWallet.getSilverValue()
    private val goldValue = GameSingleton.game.currencyWallet.getGoldValue()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityResearchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapterToolsResearch = CustomAdapterToolsResearch(GameSingleton.game.tools.getToolsDiscovered())

        // Set up RecyclerView with a LinearLayoutManager
        binding.recyclerViewResearch.layoutManager = LinearLayoutManager(this)

        // Attach the adapter to the RecyclerView
        binding.recyclerViewResearch.adapter = customAdapterToolsResearch

        binding.searchButton.setOnClickListener {

            val count = getCountForSearch()

            if(GameSingleton.game.searchThroughStones(count)){

                Toast.makeText(applicationContext, "You have serched through stones!", Toast.LENGTH_SHORT).show()

            }else{

                Toast.makeText(applicationContext, "Not enough stone!", Toast.LENGTH_SHORT).show()

            }


        }

        binding.buyButton.setOnClickListener {

            val count = getCountForBuy()

            val selectedItem = when (binding.currencySpinner.selectedItemPosition) {
                0 -> GameSingleton.game.purchaseBronze(count)
                1 -> GameSingleton.game.purchaseSilver(count)
                2 -> GameSingleton.game.purchaseGold(count)
                else -> false
            }

            if(!selectedItem){
                Toast.makeText(applicationContext, "Not enough stone!", Toast.LENGTH_SHORT).show()
            }

        }

        val arraySpinner = arrayOf(
            "Bronze per " + bronzeValue,
            "Silver per " + silverValue,
            "Gold per " + goldValue
        )

        binding.currencySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, // Layout for spinner item
            arraySpinner
        )

        startUpdatingData()
    }

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.stonesResearchTextView.text = GameSingleton.game.wallet.getBalanceInt().toString()

                binding.currentBronzeTextView.text = GameSingleton.game.currencyWallet.getBronze().toString()
                binding.currentSilverTextView.text = GameSingleton.game.currencyWallet.getSilver().toString()
                binding.currentGoldTextView.text = GameSingleton.game.currencyWallet.getGold().toString()

                binding.finalPriceTextView.text = calculateFinalPrice().toString()

                // Delay for 0,1 second for smoother experience
                delay(100)

            }
        }
    }

    private fun getCountForBuy() : Int {

        val countString = binding.buyTextNumber.text.toString()

        return if (countString.isNotEmpty()) countString.toIntOrNull() ?: 0 else 0

    }

    private fun getCountForSearch() : Int {

        val countString = binding.searchThroughTextNumber.text.toString()

        return if (countString.isNotEmpty()) countString.toIntOrNull() ?: 0 else 0

    }

    private fun calculateFinalPrice() : Int {

        val count = getCountForBuy()

        return when (binding.currencySpinner.selectedItemPosition) {
            0 -> bronzeValue * count
            1 -> silverValue * count
            2 -> goldValue * count
            else -> 0
        }

    }

}