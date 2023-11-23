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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyApplication
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentNotesBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.FragmentReminderBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.NewTaskSheet
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemAdapter
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemModelFactory
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class Notes : Fragment(), NoteActionsListener {
    private lateinit var binding: FragmentNotesBinding // kenns

    private val notesViewModel: NotesViewModel by viewModels {
        NoteModelFactory((requireActivity().application as PlantBuddyApplication).noteRepository) // kenns
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton3.setOnClickListener {
            AddNoteActivity(null).show(childFragmentManager, "newNoteTag")                        // Activity function pa
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        notesViewModel.notesList.observe(viewLifecycleOwner) {
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = NotesAdapter(it, this@Notes)
            }
        }
    }

    /* Edit and Delete Note */
    override fun onNoteEdit(note: Note) {
        AddNoteActivity(note).show(childFragmentManager, "newNoteTag")
//        notesViewModel.updateNote(note)
    }

    override fun onNoteDelete(note: Note) {
        notesViewModel.deleteNote(note)
    }


}