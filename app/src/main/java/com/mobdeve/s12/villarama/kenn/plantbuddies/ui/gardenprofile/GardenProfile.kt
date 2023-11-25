package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyApplication
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentGardenProfileBinding


class GardenProfile : Fragment(), GardenProfileClickListener {

    private lateinit var binding: FragmentGardenProfileBinding
//    private lateinit var binding2: WelcomePageBinding
    private lateinit var usernameViewModel: UsernameViewModel

    private val gardenProfileViewModel: GardenProfileViewModel by viewModels {
        PlantItemModelFactory((requireActivity().application as PlantBuddyApplication).plantRepository) // kenns
    }

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGardenProfileBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameViewModel = ViewModelProvider(requireActivity()).get(UsernameViewModel::class.java)
        usernameViewModel.username.observe(viewLifecycleOwner) { username ->
            binding.tvUsername.text = username
        }

        binding.btnAddPlant.setOnClickListener {
            try {
                NewPlantSheet(null).show(childFragmentManager, "newPlantTag")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {

        gardenProfileViewModel.plantList.observe(viewLifecycleOwner){
            binding.rvPlantCardList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = GardenProfileAdapter(it, this@GardenProfile)


            }
        }
    }

    override fun onPlantEdit(plant: PlantItem) {
        NewPlantSheet(plant).show(childFragmentManager, "newPlantTag")
    }

    override fun onPlantDelete(plant: PlantItem) {
        gardenProfileViewModel.deletePlant(plant)
    }

}