package com.example.wallproject.Model.CustomAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomViewHolderToolsResearch(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val toolImageView: ImageView = itemView.findViewById(R.id.toolImageView)
    val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    val goldTextView: TextView = itemView.findViewById(R.id.goldTextView)
    val silverTextView: TextView = itemView.findViewById(R.id.silverTextView)
    val bronzeTextView: TextView = itemView.findViewById(R.id.bronzeTextView)
    val goldImageView : ImageView = itemView.findViewById(R.id.goldImageView)
    val silverImageView : ImageView = itemView.findViewById(R.id.silverImageView)
    val bronzeImageView : ImageView = itemView.findViewById(R.id.bronzeImageView)
    val levelTextView: TextView = itemView.findViewById(R.id.levelTextView)
    val levelSimpleTextView : TextView = itemView.findViewById(R.id.levelSimpleTextView)
    val researchCostTextView: TextView = itemView.findViewById(R.id.researchCostTextView)
    val researchCostSimpleTextView: TextView = itemView.findViewById(R.id.researchCostSimpleTextView)
    val addButton: FloatingActionButton = itemView.findViewById(R.id.addButton)
}