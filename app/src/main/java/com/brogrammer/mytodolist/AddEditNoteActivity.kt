package com.brogrammer.mytodolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var noteTitleEdt:EditText
    private lateinit var addUpdateBtn:ImageView
    private lateinit var closeBtn:ImageView
    private lateinit var viewModel: NoteViewModel
    private var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt=findViewById(R.id.todoEt)
        addUpdateBtn=findViewById(R.id.todoNextBtn)
        closeBtn=findViewById(R.id.todoClose)

        //to automatically open the keyboard whenever the AddEditNoteActivity is displayed
        if (noteTitleEdt != null && noteTitleEdt.requestFocus()) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(noteTitleEdt, InputMethodManager.SHOW_IMPLICIT)
        }

        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)


        val noteType=intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle=intent.getStringExtra("noteTitle")
            noteID=intent.getIntExtra("noteID",-1)
            noteTitleEdt.setText(noteTitle)
            
            //to set the cursor at the end of the to do task while performing the editing of the to do task
            noteTitleEdt.setSelection(noteTitleEdt.text.length)
        }

        addUpdateBtn.setOnClickListener {
            val noteTitle=noteTitleEdt.text.toString()

            if(noteType.equals("Edit"))
            {
                if(noteTitle.isNotEmpty())
                {
                    val updateNote=Note(noteTitle)
                    updateNote.id=noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"To Do Updated!",Toast.LENGTH_SHORT).show()
                }
            }else
            {
                if(noteTitle.isNotEmpty())
                {
                    viewModel.addNote(Note(noteTitle))
                    Toast.makeText(this,"To Do Added!",Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext,TaskActivity::class.java))
            this.finish()
        }

        //to submit the to do task using device keyboard submit button
        noteTitleEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                val noteTitle = noteTitleEdt.text.toString()

                if (noteType == "Edit") {
                    if (noteTitle.isNotEmpty()) {
                        val updateNote = Note(noteTitle)
                        updateNote.id = noteID
                        viewModel.updateNote(updateNote)
                        Toast.makeText(this, "To Do Updated!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (noteTitle.isNotEmpty()) {
                        viewModel.addNote(Note(noteTitle))
                        Toast.makeText(this, "To Do Added!", Toast.LENGTH_SHORT).show()
                    }
                }
                startActivity(Intent(applicationContext, TaskActivity::class.java))
                this.finish()
                true
            } else {
                false
            }
        }


        closeBtn.setOnClickListener {
            startActivity(Intent(applicationContext,TaskActivity::class.java))
            this.finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(applicationContext,TaskActivity::class.java))
        this.finish()
    }
}