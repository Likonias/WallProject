package com.example.wallproject.Model.CustomAdapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.Model.ToolImageMapper
import com.example.wallproject.Model.Wall.Tool
import com.example.wallproject.R

class CustomAdapterToolsResearch(private val toolsList: List<Tool>) :
    RecyclerView.Adapter<CustomViewHolderToolsResearch>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderToolsResearch {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tool_list_custom_research, parent, false)
        return CustomViewHolderToolsResearch(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolderToolsResearch, position: Int) {
        val tool = toolsList[position]

        ToolImageMapper.toolImageMap[tool.id]?.let { holder.toolImageView.setImageResource(it) }
        holder.nameTextView.text = tool.name

        holder.goldTextView.text = tool.upgradeCost.gold.toString()
        holder.silverTextView.text = tool.upgradeCost.silver.toString()
        holder.bronzeTextView.text = tool.upgradeCost.bronze.toString()

        holder.levelTextView.text = tool.level.toString()

        holder.researchCostTextView.text = tool.researchCost.toString()

        if(tool.isResearched){

            showUpgrade(holder)

        }else{

            showResearch(holder)

        }

        //todo finish buttons

        holder.addButton.setOnClickListener {

            if(tool.isResearched){
                GameSingleton.game.upgradeTool(tool.id)
            }else{
                if(GameSingleton.game.researchTool(tool.id)){
                    showUpgrade(holder)
                }
            }

            // Notify the adapter about the change in the dataset for the specific item
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return toolsList.size
    }

    private fun showUpgrade(holder: CustomViewHolderToolsResearch) {

        holder.goldTextView.visibility = View.VISIBLE
        holder.goldImageView.visibility = View.VISIBLE
        holder.silverTextView.visibility = View.VISIBLE
        holder.silverImageView.visibility = View.VISIBLE
        holder.bronzeTextView.visibility = View.VISIBLE
        holder.bronzeImageView.visibility = View.VISIBLE
        holder.levelTextView.visibility = View.VISIBLE
        holder.levelSimpleTextView.visibility = View.VISIBLE

        holder.researchCostTextView.visibility = View.INVISIBLE
        holder.researchCostSimpleTextView.visibility = View.INVISIBLE

    }

    private fun showResearch(holder: CustomViewHolderToolsResearch) {

        holder.goldTextView.visibility = View.INVISIBLE
        holder.goldImageView.visibility = View.INVISIBLE
        holder.silverTextView.visibility = View.INVISIBLE
        holder.silverImageView.visibility = View.INVISIBLE
        holder.bronzeTextView.visibility = View.INVISIBLE
        holder.bronzeImageView.visibility = View.INVISIBLE
        holder.levelTextView.visibility = View.INVISIBLE
        holder.levelSimpleTextView.visibility = View.INVISIBLE

        holder.researchCostTextView.visibility = View.VISIBLE
        holder.researchCostSimpleTextView.visibility = View.VISIBLE

    }

}