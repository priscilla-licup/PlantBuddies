package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNotesBinding
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class Notes : Fragment() {

    private var _binding: FragmentNotesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // RecyclerView variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private var notesList: MutableList<Note> = mutableListOf()

    //
    private val ADD_NOTE_REQUEST = 1

    // Adding a new note
    private fun showAddNoteDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_add_note, null)
        val dialogTitle = "Add New Note"
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle(dialogTitle)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Add", null)

        val alertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val titleEditText = dialogView.findViewById<EditText>(R.id.editTextNoteTitle)
            val contentEditText = dialogView.findViewById<EditText>(R.id.editTextNoteContent)

            val title = titleEditText.text.toString().trim()
            val content = contentEditText.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                // Generate the current date
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val currentDate = dateFormat.format(Date())

                // Create a new note with the date
                val newNote = Note(Random.nextInt(), title, content, currentDate)
                addNewNote(newNote)
                alertDialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter both title and content", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addNewNote(note: Note) {
        notesList.add(note)
        notesAdapter.notifyItemInserted(notesList.size - 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notesViewModel.text.observe(viewLifecycleOwner) {
          textView.text = it
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        notesAdapter = NotesAdapter(notesList)
        recyclerView.adapter = notesAdapter

        binding.floatingActionButton3.setOnClickListener {
            // Show a dialog or navigate to another fragment to add a new note
            // showAddNoteDialog()
            val intent = Intent(context, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val title = it.getStringExtra("EXTRA_NOTE_TITLE") ?: ""
                val content = it.getStringExtra("EXTRA_NOTE_CONTENT") ?: ""
                val date = it.getStringExtra("EXTRA_NOTE_DATE") ?: ""

                // Create a new Note object and add it to your list
                val newNote = Note(Random.nextInt(), title, content, date)
                addNewNote(newNote)
            }
        }
    }

}