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
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNewPlantSheetBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.Note
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NoteModelFactory
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NotesRepository
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NotesViewModel

class NewPlantSheet(var plant: PlantItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewPlantSheetBinding
    private lateinit var gardenProfileViewModel: GardenProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewPlantSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantDao = PlantBuddyDatabase.getDatabase(requireContext()).plantDao()
        val repository = PlantRepository(plantDao)
        val factory = PlantItemModelFactory(repository)
        gardenProfileViewModel = ViewModelProvider(this, factory).get(GardenProfileViewModel::class.java)

        binding.btnAddNewPlant.setOnClickListener{
            addNewPlant()
        }
    }

    private fun addNewPlant() {
        val newPlantName = binding.etNewPlantName.text.toString()
        val newPlantDesc = binding.etNewPlantDesc.text.toString()

        val newPlant = PlantItem(newPlantName, newPlantDesc, null)
        gardenProfileViewModel.addNewPlant(newPlant)

        Log.d("NewPlantSheet", "Adding new plant: $newPlantName: $newPlantDesc")

        binding.etNewPlantName.setText("")
        binding.etNewPlantDesc.setText("")

        dismiss()
    }

}