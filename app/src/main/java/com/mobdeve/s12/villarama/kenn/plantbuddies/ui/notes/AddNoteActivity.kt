package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentAddNoteBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItem
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemModelFactory
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemRepository
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AddNoteActivity(var note: Note?) : BottomSheetDialogFragment() {

    private val SELECT_PICTURE_REQUEST = 3
    private var selectedImageUri: Uri? = null
    private var currentDateString: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Editing Note
        if (note != null)
        {
            val editable = Editable.Factory.getInstance()

            binding.etNoteDate.text = editable.newEditable(note!!.date)
            binding.etNoteTitle.text = editable.newEditable(note!!.title)
            binding.etNoteContent.text = editable.newEditable(note!!.content)
            binding.toggleButton3.isChecked = note!!.toggleShovel
            binding.toggleButton4.isChecked = note!!.toggleWater
            binding.toggleButton5.isChecked = note!!.toggleSeeds
            binding.toggleButton6.isChecked = note!!.toggleInsect
            binding.toggleButton7.isChecked = note!!.toggleHarvest

            val existingImageUri = note!!.imageUri
            if (!existingImageUri.isNullOrEmpty()) {
                selectedImageUri = Uri.parse(existingImageUri)
                binding.ivNoteImage.setImageURI(selectedImageUri)
            }
        } else {
            binding.etNoteDate.text = currentDateString
        }

        // try to fix class instance error
        val noteDao = TaskItemDatabase.getDatabase(requireContext()).noteDao()
        val repository = NotesRepository(noteDao)
        val factory = NoteModelFactory(repository)
        notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)

        binding.btnSaveNote.setOnClickListener {
            saveNote()
        }
        binding.btnSelectImage.setOnClickListener {
            chooseImage()
        }
        binding.etNoteDate.setOnClickListener {
            openDatePicker()
        }

    }

    private fun saveNote() {
        val title = binding.etNoteTitle.text.toString()
        val content = binding.etNoteContent.text.toString()
        val noteDate = binding.etNoteDate.text.toString()

        // Retrieve toggle states
        val shovelToggle = binding.toggleButton3.isChecked
        val waterToggle = binding.toggleButton4.isChecked
        val seedsToggle = binding.toggleButton5.isChecked
        val insectToggle = binding.toggleButton6.isChecked
        val harvestToggle = binding.toggleButton7.isChecked

        // Retrieve the image URI (assuming it's saved as a member variable)
        val imageUri = selectedImageUri?.toString()

        val note = Note(title, content, noteDate, imageUri, shovelToggle, waterToggle, seedsToggle, insectToggle, harvestToggle)

        if (note == null) {
            notesViewModel.addNewNote(note)
        } else {
            notesViewModel.updateNote(note)
        }
        binding.etNoteDate.text = ""
        binding.etNoteTitle.setText("")
        binding.etNoteContent.setText("")
        binding.ivNoteImage.setImageURI(null)
        binding.toggleButton3.isChecked = false
        binding.toggleButton4.isChecked = false
        binding.toggleButton5.isChecked = false
        binding.toggleButton6.isChecked = false
        binding.toggleButton7.isChecked = false

        dismiss()
    }
    private fun chooseImage() {
        val selectFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(selectFileIntent, SELECT_PICTURE_REQUEST)
    }
    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth).format(
                DateTimeFormatter.ISO_DATE)
            binding.etNoteDate.text = selectedDate
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }



    // -------------------------------------



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PICTURE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.ivNoteImage.setImageURI(selectedImageUri)

                // Only take persistable permission if the URI is not null
//                contentResolver.takePersistableUriPermission(
//                    uri,
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                )
            }
        }
    }
}
