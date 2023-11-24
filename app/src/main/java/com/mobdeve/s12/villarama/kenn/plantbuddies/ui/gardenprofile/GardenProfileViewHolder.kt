package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.PlantItemCellBinding

class GardenProfileViewHolder(
    private val context: Context,
    private val binding: PlantItemCellBinding,
    private val clickListener: GardenProfileClickListener
): RecyclerView.ViewHolder(binding.root) {

    fun bindPlantItem(plant: PlantItem) {
//        binding.noteTitleTextView.text = note.title
//        binding.noteContentTextView.text = note.content

        binding.cvPlantCard.setOnClickListener{
            clickListener.onPlantEdit(plant)
        }

        binding.btnDeletePlantCell.setOnClickListener{
            clickListener.onPlantDelete(plant)
        }

    }

}