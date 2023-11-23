package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobdeve.s12.villarama.kenn.plantbuddies.MainActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNewPlantSheetBinding

class NewPlantSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewPlantSheetBinding
    private lateinit var gardenProfileViewModel: GardenProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gardenProfileViewModel = ViewModelProvider(requireActivity()).get(GardenProfileViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewPlantSheetBinding.inflate(inflater, container, false)

        binding.btnAddNewPlant.setOnClickListener{
            addNewPlant()
        }

        return binding.root
    }

    private fun addNewPlant() {
        val newPlantName = binding.etNewPlantName.text.toString()
        val newPlantDesc = binding.etNewPlantDesc.text.toString()

        Log.d("NewPlantSheet", "Adding new plant: $newPlantName, $newPlantDesc")

        gardenProfileViewModel.plantName.value = newPlantName
        gardenProfileViewModel.plantDesc.value = newPlantDesc
        binding.etNewPlantName.setText("")
        binding.etNewPlantDesc.setText("")
        dismiss()
    }

}