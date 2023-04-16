package com.brogrammer.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    lateinit var notesRV:RecyclerView
    lateinit var addFAB: ImageView
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        notesRV=findViewById(R.id.taskRecyclerView)
        addFAB=findViewById(R.id.addTaskBtn)
        notesRV.layoutManager=LinearLayoutManager(this)

        //to manage space between the items in the list of recycler view
        notesRV.addItemDecoration(ItemSpacingDecoration(-30))

        val noteRVAdapter=NoteRVAdapter(this,this,this)
        {pos,newCheckedState ->
            viewModel.allNotes.value?.get(pos)?.let {
               viewModel.updateNote(
                   it.apply {
                       checked = newCheckedState
                   }
               )
            }
        }
        notesRV.adapter=noteRVAdapter

        viewModel= ViewModelProvider(this)[NoteViewModel::class.java]

        viewModel.allNotes.observe(this, Observer { list->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            val intent=Intent(this@TaskActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"To Do Deleted",Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent=Intent(this@TaskActivity,AddEditNoteActivity::class.java)
        intent.putExtra("noteType","Edit");
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteID",note.id)
        startActivity(intent)
        this.finish()

    }
}