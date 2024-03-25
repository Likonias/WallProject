package com.example.wallproject.Model.CustomAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.Model.ToolImageMapper
import com.example.wallproject.Model.Wall.Tool
import com.example.wallproject.R

class CustomAdapterToolsResearch(private val toolsList: List<Tool>) :
    RecyclerView.Adapter<CustomViewHolderToolsAdd>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderToolsAdd {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tool_list_custom_add, parent, false)
        return CustomViewHolderToolsAdd(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolderToolsAdd, position: Int) {
        val tool = toolsList[position]

        ToolImageMapper.toolImageMap[tool.id]?.let { holder.toolImageView.setImageResource(it) }
        holder.nameTextView.text = tool.name
        holder.miningPowerTextView.text = tool.getMiningPowerFormated().toString()
        holder.timeToMineTextView.text = tool.getTimeToMineFormated().toString()
        holder.addCostTextView.text = tool.cost.toString()
        holder.countTextView.text = tool.count.toString()

        holder.addButton.setOnClickListener {
            GameSingleton.game.addTool(tool.id)

            holder.countTextView.text = tool.count.toString()

            // Notify the adapter about the change in the dataset for the specific item
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return toolsList.size
    }
}