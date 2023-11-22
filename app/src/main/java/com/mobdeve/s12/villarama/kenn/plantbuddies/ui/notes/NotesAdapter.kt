package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.R

interface NoteActionsListener {
    fun onNoteEdit(note: Note)
    fun onNoteDelete(note: Note)
}
class NotesAdapter(
    private var notesList: MutableList<Note>,
    private val listener: NoteActionsListener
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.noteContentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.noteDateTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteNoteButton)
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
        holder.itemView.setOnClickListener {
            // Invoke edit action
            listener.onNoteEdit(currentNote)
        }
        holder.deleteButton.setOnClickListener {
            // Invoke delete action
            listener.onNoteDelete(currentNote)
        }
    }

    override fun getItemCount() = notesList.size

}
