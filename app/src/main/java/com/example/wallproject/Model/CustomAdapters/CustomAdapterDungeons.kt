package com.example.wallproject.Model.CustomAdapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.wallproject.Model.Dungeon.Dungeon
import com.example.wallproject.Model.DungeonImageMapper
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.Model.ToolImageMapper
import com.example.wallproject.R

class CustomAdapterDungeons(private val dungeonList: List<Dungeon>) :
    RecyclerView.Adapter<CustomViewHolderDungeons>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderDungeons {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dungeon_list_custom, parent, false)
        return CustomViewHolderDungeons(view)
    }

    override fun getItemCount(): Int {
        return dungeonList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolderDungeons, position: Int) {
        val dungeon = dungeonList[position]

//        DungeonImageMapper.dungeonEnterance[dungeon.id]?.let { holder.dungeonImageView.setImageResource(it) }
//        holder.nameTextView.text = tool.name




        //todo finish buttons

//        holder.addButton.setOnClickListener {
//
//
//
//            // Notify the adapter about the change in the dataset for the specific item
//            notifyItemChanged(position)
//        }
    }


}