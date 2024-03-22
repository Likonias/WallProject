package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDefaultScreenBinding
import com.example.wallproject.databinding.ActivityToolsScreenBinding

class ToolsScreen : AppCompatActivity() {

    private lateinit var binding: ActivityToolsScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityToolsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}