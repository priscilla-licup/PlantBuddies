package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.NoteItemBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.TaskItemCellBinding

class NotesAdapter(
    private val notesList: List<Note>, // kenn has List not MutableList
    private val listener: NoteActionsListener
) : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        val from = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(from, parent, false)
        return NoteViewHolder(parent.context, binding, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(notesList[position])
    }

    override fun getItemCount(): Int = notesList.size

}