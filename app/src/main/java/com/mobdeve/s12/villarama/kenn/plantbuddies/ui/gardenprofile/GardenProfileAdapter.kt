package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.PlantItemCellBinding

class GardenProfileAdapter (
    private val plantList: List<PlantItem>, // kenn has List not MutableList
    private val listener: GardenProfileClickListener
) : RecyclerView.Adapter<GardenProfileViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenProfileViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = PlantItemCellBinding.inflate(from, parent, false)
        return GardenProfileViewHolder(parent.context, binding, listener)
    }

    override fun onBindViewHolder(holder: GardenProfileViewHolder, position: Int) {
        holder.bindPlantItem(plantList[position])

    }

    override fun getItemCount(): Int {
        return plantList.size
    }
}