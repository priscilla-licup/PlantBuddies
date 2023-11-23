package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {

    private val SELECT_PICTURE_REQUEST = 3
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Check if there's existing data passed to this activity
        val existingNoteId = intent.getIntExtra("EXTRA_NOTE_ID", 0)
        val existingDate = intent.getStringExtra("EXTRA_NOTE_DATE")
        val existingTitle = intent.getStringExtra("EXTRA_NOTE_TITLE")
        val existingContent = intent.getStringExtra("EXTRA_NOTE_CONTENT")
        val existingImageUri = intent.getStringExtra("EXTRA_IMAGE_URI")
        val existingShovelToggle = intent.getBooleanExtra("EXTRA_SHOVEL_TOGGLE", false)
        val existingWaterToggle = intent.getBooleanExtra("EXTRA_WATER_TOGGLE", false)
        val existingSeedsToggle = intent.getBooleanExtra("EXTRA_SEEDS_TOGGLE", false)
        val existingInsectToggle = intent.getBooleanExtra("EXTRA_INSECT_TOGGLE", false)
        val existingHarvestToggle = intent.getBooleanExtra("EXTRA_HARVEST_TOGGLE", false)

        Log.d("AddNoteActivity", "Received Image URI for Editing: $existingImageUri")
        Log.d("AddNoteActivity", "Received note ID: $existingNoteId")
        Log.d("AddNoteActivity", "Received note Title: $existingTitle")
        Log.d("AddNoteActivity", "Received note Content: $existingContent")

        var dateEditText = findViewById<TextView>(R.id.etNoteDate) // Replace with your actual EditText ID
        var currentDateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//        dateEditText.setText(currentDateString)
        Log.d("AddNoteActivity", "Current date: $currentDateString")
        findViewById<TextView>(R.id.etNoteDate).text = currentDateString

        // Populate fields if existing data is present
        if (existingNoteId != -1) {
            findViewById<TextView>(R.id.etNoteDate).text = existingDate
            findViewById<EditText>(R.id.etNoteTitle).setText(existingTitle)
            findViewById<EditText>(R.id.etNoteContent).setText(existingContent)
            findViewById<ToggleButton>(R.id.toggleButton3).isChecked = existingShovelToggle
            findViewById<ToggleButton>(R.id.toggleButton4).isChecked = existingWaterToggle
            findViewById<ToggleButton>(R.id.toggleButton5).isChecked = existingSeedsToggle
            findViewById<ToggleButton>(R.id.toggleButton6).isChecked = existingInsectToggle
            findViewById<ToggleButton>(R.id.toggleButton7).isChecked = existingHarvestToggle

            if (!existingImageUri.isNullOrEmpty()) {
                selectedImageUri = Uri.parse(existingImageUri)
                findViewById<ImageView>(R.id.ivNoteImage).setImageURI(selectedImageUri)
            }
        } else {
            findViewById<TextView>(R.id.etNoteDate).text = currentDateString
        }

        // Button for adding/editing note
        val btnSaveNote = findViewById<Button>(R.id.btnSaveNote)
        btnSaveNote.setOnClickListener {
            val title = findViewById<EditText>(R.id.etNoteTitle).text.toString()
            val content = findViewById<EditText>(R.id.etNoteContent).text.toString()
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val currentDate = dateFormat.format(Date())
            val noteDate = findViewById<TextView>(R.id.etNoteDate).text.toString()

            // Retrieve toggle states
            val shovelToggle = findViewById<ToggleButton>(R.id.toggleButton3).isChecked
            val waterToggle = findViewById<ToggleButton>(R.id.toggleButton4).isChecked
            val seedsToggle = findViewById<ToggleButton>(R.id.toggleButton5).isChecked
            val insectToggle = findViewById<ToggleButton>(R.id.toggleButton6).isChecked
            val harvestToggle = findViewById<ToggleButton>(R.id.toggleButton7).isChecked

            // Retrieve the image URI (assuming it's saved as a member variable)
            val imageUri = selectedImageUri?.toString()


            val note = Note(
                id = existingNoteId, // For new notes, this should be 0 (autoGenerate will handle it)
                title = title,
                content = content,
                date = noteDate,
                imageUri = imageUri,
                toggleShovel = shovelToggle,
                toggleWater = waterToggle,
                toggleSeeds = seedsToggle,
                toggleInsect = insectToggle,
                toggleHarvest = harvestToggle
            )

            // Get the ViewModel instance
            val notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

            if (existingNoteId == 0) {
                notesViewModel.addNewNote(note) // Add new note
            } else {
                notesViewModel.updateNote(note) // Update existing note
            }

            finish()  // Finish the activity
        }

        // Button for adding pictures to note
        findViewById<Button>(R.id.btnSelectImage).setOnClickListener {

            val selectFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(selectFileIntent, SELECT_PICTURE_REQUEST)
        }

        // To change date in adding/editing notes
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth).format(
                    DateTimeFormatter.ISO_DATE)
                dateEditText.text = selectedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PICTURE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                findViewById<ImageView>(R.id.ivNoteImage).setImageURI(uri)

                // Only take persistable permission if the URI is not null
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    }
}
