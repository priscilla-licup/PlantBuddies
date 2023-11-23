package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNotesBinding
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class Notes : Fragment(), NoteActionsListener {

    private val notesViewModel: NotesViewModel by lazy {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }
    private var _binding: FragmentNotesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // RecyclerView variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private var notesList: MutableList<Note> = mutableListOf()

    //
    private val ADD_NOTE_REQUEST = 1
    private val EDIT_NOTE_REQUEST = 2

//    private fun addNewNote(note: Note) {
//        notesList.add(note)
//        notesAdapter.notifyItemInserted(notesList.size - 1)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the adapter with an empty list or initial data
        notesAdapter = NotesAdapter(mutableListOf(), this)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = notesAdapter

        notesViewModel.notesList.observe(viewLifecycleOwner, Observer { updatedList ->
            // Convert the List to a MutableList
            Log.d("NotesFragment", "Updated notes list: $updatedList")
            notesAdapter.notesList = updatedList.toMutableList()
            notesAdapter.notifyDataSetChanged()

        })

        binding.floatingActionButton3.setOnClickListener {
            // Show a dialog or navigate to another fragment to add a new note
            // showAddNoteDialog()
            val intent = Intent(context, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

//        notesViewModel.notesList.observe(viewLifecycleOwner, Observer { updatedList ->
//            notesList.clear()
//            notesList.addAll(updatedList)
//            notesAdapter.notifyDataSetChanged()
//        })


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
                val noteId = it.getIntExtra("EXTRA_NOTE_ID", 0)
                val title = it.getStringExtra("EXTRA_NOTE_TITLE") ?: ""
                val content = it.getStringExtra("EXTRA_NOTE_CONTENT") ?: ""
                val date = it.getStringExtra("EXTRA_NOTE_DATE") ?: ""
                val imageUri = it.getStringExtra("EXTRA_IMAGE_URI")
                val shovelToggle = it.getBooleanExtra("EXTRA_SHOVEL_TOGGLE", false)
                val waterToggle = it.getBooleanExtra("EXTRA_WATER_TOGGLE", false)
                val seedsToggle = it.getBooleanExtra("EXTRA_SEEDS_TOGGLE", false)
                val insectToggle = it.getBooleanExtra("EXTRA_INSECT_TOGGLE", false)
                val harvestToggle = it.getBooleanExtra("EXTRA_HARVEST_TOGGLE", false)

                val newNote = Note(noteId, title, content, date, imageUri, shovelToggle, waterToggle, seedsToggle, insectToggle, harvestToggle)
                notesViewModel.addNewNote(newNote)
                Log.d("NotesFragment", "New note added: $newNote")

            }
            notesAdapter.notifyDataSetChanged()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val noteId = it.getIntExtra("EXTRA_NOTE_ID", 0)
                val title = it.getStringExtra("EXTRA_NOTE_TITLE") ?: ""
                val content = it.getStringExtra("EXTRA_NOTE_CONTENT") ?: ""
                val date = it.getStringExtra("EXTRA_NOTE_DATE") ?: ""
                val imageUri = it.getStringExtra("EXTRA_IMAGE_URI")
                val shovelToggle = it.getBooleanExtra("EXTRA_SHOVEL_TOGGLE", false)
                val waterToggle = it.getBooleanExtra("EXTRA_WATER_TOGGLE", false)
                val seedsToggle = it.getBooleanExtra("EXTRA_SEEDS_TOGGLE", false)
                val insectToggle = it.getBooleanExtra("EXTRA_INSECT_TOGGLE", false)
                val harvestToggle = it.getBooleanExtra("EXTRA_HARVEST_TOGGLE", false)

                val updatedNote = Note(noteId, title, content, date, imageUri, shovelToggle, waterToggle, seedsToggle, insectToggle, harvestToggle)
                notesViewModel.updateNote(updatedNote)
                Log.d("NotesFragment", "Updated note: $updatedNote")
            }
            notesAdapter.notifyDataSetChanged()
        }
    }

    /* Edit and Delete Note */
    override fun onNoteEdit(note: Note) {
        Log.d("NotesFragment", "Editing note: $note")

        val editIntent = Intent(requireContext(), AddNoteActivity::class.java).apply {
            putExtra("EXTRA_NOTE_ID", note.id)
            putExtra("EXTRA_NOTE_DATE", note.date)
            putExtra("EXTRA_NOTE_TITLE", note.title)
            putExtra("EXTRA_NOTE_CONTENT", note.content)
            putExtra("EXTRA_IMAGE_URI", note.imageUri)
            putExtra("EXTRA_SHOVEL_TOGGLE", note.toggleShovel)
            putExtra("EXTRA_WATER_TOGGLE", note.toggleWater)
            putExtra("EXTRA_SEEDS_TOGGLE", note.toggleSeeds)
            putExtra("EXTRA_INSECT_TOGGLE", note.toggleInsect)
            putExtra("EXTRA_HARVEST_TOGGLE", note.toggleHarvest)
            // Add other fields as necessary
        }

        Log.d("NotesFragment", "Passing Image URI for Edit: ${note.imageUri}")
        startActivityForResult(editIntent, EDIT_NOTE_REQUEST)  // not sure ADD_NOTE_REQUEST
    }

    override fun onNoteDelete(note: Note) {
        notesViewModel.deleteNote(note)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val notesViewModel: NotesViewModel by viewModels()
        notesViewModel.notesList.observe(viewLifecycleOwner, Observer { notes ->
            Log.d("NotesFragment", "LiveData Observer triggered with notes: $notes")
            // Update your RecyclerView adapter here
            notesAdapter.notesList = notes.toMutableList()
            notesAdapter.notifyDataSetChanged()
        })
    }
}