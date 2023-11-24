package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentGardenProfileBinding


class GardenProfile : Fragment() {

    private lateinit var binding: FragmentGardenProfileBinding
    private lateinit var gardenProfileViewModel: GardenProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gardenProfileViewModel = ViewModelProvider(this).get(GardenProfileViewModel::class.java)
        binding = FragmentGardenProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPlant.setOnClickListener {
            NewPlantSheet().show(childFragmentManager, "newPlantTag")
        }

        gardenProfileViewModel.plantName.observe(viewLifecycleOwner){newName ->
            binding.tvPlantNameTemp.text = String.format("Plant Name: %s", newName)
        }
        gardenProfileViewModel.plantDesc.observe(viewLifecycleOwner){newDesc ->
            binding.tvPlantDescTemp.text = String.format("Plant Name: %s", newDesc)
        }
    }

}