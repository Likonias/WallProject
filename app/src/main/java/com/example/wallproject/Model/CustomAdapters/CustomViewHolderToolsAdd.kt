package com.example.wallproject.Model.CustomAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomViewHolderToolsAdd(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val toolImageView: ImageView = itemView.findViewById(R.id.toolImageView)
    val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    val countTextView: TextView = itemView.findViewById(R.id.countTextView)
    val addCostTextView: TextView = itemView.findViewById(R.id.addCostTextView)
    val timeToMineTextView: TextView = itemView.findViewById(R.id.timeToMineTextView)
    val miningPowerTextView: TextView = itemView.findViewById(R.id.miningPowerTextView)
    val addButton: FloatingActionButton = itemView.findViewById(R.id.addButton)
}