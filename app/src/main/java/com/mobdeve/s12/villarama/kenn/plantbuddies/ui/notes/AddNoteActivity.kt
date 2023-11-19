package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import java.util.Date
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val btnSaveNote = findViewById<Button>(R.id.btnSaveNote)
        btnSaveNote.setOnClickListener {
            val title = findViewById<EditText>(R.id.etNoteTitle).text.toString()
            val content = findViewById<EditText>(R.id.etNoteContent).text.toString()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            // Validate input...

            // Create an Intent to hold the results
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_NOTE_TITLE", title)
            resultIntent.putExtra("EXTRA_NOTE_CONTENT", content)
            resultIntent.putExtra("EXTRA_NOTE_DATE", currentDate)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()  // Finish the activity
        }

    }

    // Additional methods if needed
}