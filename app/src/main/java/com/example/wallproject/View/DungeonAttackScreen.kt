package com.example.wallproject.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityDungeonAttackScreenBinding
import com.example.wallproject.databinding.ActivityDungeonsScreenBinding

class DungeonAttackScreen : AppCompatActivity() {

    private lateinit var binding: ActivityDungeonAttackScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDungeonAttackScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}