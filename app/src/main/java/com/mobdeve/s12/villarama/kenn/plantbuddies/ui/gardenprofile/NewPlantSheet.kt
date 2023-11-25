package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNewPlantSheetBinding


class NewPlantSheet(var plant: PlantItem?) : BottomSheetDialogFragment() {

    private val CAMERA_REQUEST = 123

    private val SELECT_PICTURE_REQUEST = 3
    private var selectedImageUri: Uri? = null

    private lateinit var binding: FragmentNewPlantSheetBinding
    private lateinit var gardenProfileViewModel: GardenProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewPlantSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (plant != null) {
            val editable = Editable.Factory.getInstance()

//            binding.etNoteDate.text = editable.newEditable(note!!.date)
//            binding.etNoteTitle.text = editable.newEditable(note!!.title)
            binding.tvNewPlant.text = "Edit Plant"
            binding.btnAddNewPlant.text = "Save Changes"
            binding.btnTakeImage.text = "Take Image"
            binding.btnChooseImage.text = "Choose Image"

            binding.etNewPlantName.text = editable.newEditable(plant!!.plantName)
            binding.etNewPlantDesc.text = editable.newEditable(plant!!.plantDesc)

            val existingImageUri = plant!!.imageUri
            if (!existingImageUri.isNullOrEmpty()) {
                selectedImageUri = Uri.parse(existingImageUri)
                binding.ivPlantImage.setImageURI(selectedImageUri)
            }
        }

        val plantDao = PlantBuddyDatabase.getDatabase(requireContext()).plantDao()
        val repository = PlantRepository(plantDao)
        val factory = PlantItemModelFactory(repository)
        gardenProfileViewModel = ViewModelProvider(this, factory).get(GardenProfileViewModel::class.java)

        binding.btnAddNewPlant.setOnClickListener{
            savePlant()
        }

        binding.btnTakeImage.setOnClickListener {
            takeImage()
        }

        binding.btnChooseImage.setOnClickListener {
            chooseImage()
        }
    }


    private fun savePlant() {
        val newPlantName = binding.etNewPlantName.text.toString()
        val newPlantDesc = binding.etNewPlantDesc.text.toString()

        val imageUri = selectedImageUri?.toString()

        if(plant == null){
            val newPlant = PlantItem(newPlantName, newPlantDesc, imageUri)
            gardenProfileViewModel.addNewPlant(newPlant)
        } else {
            plant!!.plantName = newPlantName
            plant!!.plantDesc = newPlantDesc
            plant!!.imageUri = imageUri

            gardenProfileViewModel.updatePlant(plant!!)
        }

        Log.d("NewPlantSheet", "Adding new plant: $newPlantName: $newPlantDesc")

        binding.etNewPlantName.setText("")
        binding.etNewPlantDesc.setText("")
        binding.ivPlantImage.setImageURI(null)

        dismiss()
    }

    private fun takeImage() {
//        val Intent3 = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
//        startActivity(Intent3)

        // WORKING ON IT
        val camera_intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
        startActivityForResult(camera_intent, CAMERA_REQUEST)
    }

    private fun chooseImage(){
        val selectFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(selectFileIntent, SELECT_PICTURE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PICTURE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.ivPlantImage.setImageURI(selectedImageUri)
            }
        } else if (requestCode == CAMERA_REQUEST) {

            chooseImage()

            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.ivPlantImage.setImageURI(selectedImageUri)
            }
        }
    }


}