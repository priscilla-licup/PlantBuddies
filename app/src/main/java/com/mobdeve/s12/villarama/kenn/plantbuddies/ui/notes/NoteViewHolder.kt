package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.NoteItemBinding

class NoteViewHolder (
    private val context: Context,
    private val binding: NoteItemBinding,
    private val clickListener: NoteActionsListener
): RecyclerView.ViewHolder(binding.root)
{

    fun bindNote(note: Note) {
        binding.noteTitleTextView.text = note.title
        binding.noteContentTextView.text = note.content
        binding.noteDateTextView.text = note.date

        binding.noteItemView.setOnClickListener{
            clickListener.onNoteEdit(note)
        }
        binding.deleteNoteButton.setOnClickListener {
            clickListener.onNoteDelete(note)
        }

    }
//    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
//        // Bind data to the views
//        val currentNote = notesList[position]
//        holder.titleTextView.text = currentNote.title
//        holder.contentTextView.text = currentNote.content
//        holder.dateTextView.text = currentNote.date
//        holder.itemView.setOnClickListener {
//            // Invoke edit action
//            listener.onNoteEdit(currentNote)
//        }
//        holder.deleteButton.setOnClickListener {
//            // Invoke delete action
//            listener.onNoteDelete(currentNote)
//        }
//    }
//
//    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val titleTextView: TextView = itemView.findViewById(R.id.noteTitleTextView)
//        val contentTextView: TextView = itemView.findViewById(R.id.noteContentTextView)
//        val dateTextView: TextView = itemView.findViewById(R.id.noteDateTextView)
//        val deleteButton: Button = itemView.findViewById(R.id.deleteNoteButton)
//        // Other views...
//    }

}