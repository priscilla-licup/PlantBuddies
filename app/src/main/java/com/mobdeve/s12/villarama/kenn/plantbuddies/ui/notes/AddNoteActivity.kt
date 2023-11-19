package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.R

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val btnSaveNote = findViewById<Button>(R.id.btnSaveNote)
        btnSaveNote.setOnClickListener {
            val title = findViewById<EditText>(R.id.etNoteTitle).text.toString()
            val content = findViewById<EditText>(R.id.etNoteContent).text.toString()

            // Validate input, then save the note
            // ...

            // After saving, you can finish this activity to return to the main screen
            finish()
        }
    }

    // Additional methods if needed
}