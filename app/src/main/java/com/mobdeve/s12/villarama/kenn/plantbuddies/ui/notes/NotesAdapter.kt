package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.R

class NotesAdapter(private var notesList: MutableList<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.noteContentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.noteDateTextView)
        // Other views...
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // Bind data to the views
        val currentNote = notesList[position]
        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.content
        holder.dateTextView.text = currentNote.date
    }

    override fun getItemCount() = notesList.size


}
