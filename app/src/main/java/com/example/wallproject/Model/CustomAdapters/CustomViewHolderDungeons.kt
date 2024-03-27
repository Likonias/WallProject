package com.example.wallproject.Model.CustomAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wallproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomViewHolderDungeons(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val dungeonImageView: ImageView = itemView.findViewById(R.id.dungeonImageView)
    val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    val descriptionTextView : TextView = itemView.findViewById(R.id.descriptionTextView)
    val wholeDungeon : ConstraintLayout = itemView.findViewById(R.id.wholeDungeon)

}