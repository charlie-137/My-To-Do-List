package com.brogrammer.mytodolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface,
    val onCheck : (id : Int, newCheckedState : Boolean) -> Unit
    ): RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

        private val allNotes = ArrayList<Note>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val noteTV = itemView.findViewById<TextView>(R.id.todoTask)
            val editIV = itemView.findViewById<ImageView>(R.id.editTask)
            val deleteIV = itemView.findViewById<ImageView>(R.id.deleteTask)
            val checkBox = itemView.findViewById<CheckBox>(R.id.toDoCheckBox)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_task,parent,false);
        return ViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.checkBox.isChecked = allNotes[position].checked

        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        holder.editIV.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            //inside any function that will be later executed like this click listener
            //use holder.absoluteAdapterPosition to get the holder position
            onCheck(holder.absoluteAdapterPosition,isChecked)
        }
    }

    override fun getItemCount(): Int {
       return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}