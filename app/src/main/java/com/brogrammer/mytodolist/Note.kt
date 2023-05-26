package com.brogrammer.mytodolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note (
    @ColumnInfo(name = "title")
    val noteTitle:String,
//    @ColumnInfo(name = "description")
//    val noteDescription:String,
    @ColumnInfo(name = "checked")
    var checked : Boolean = false
    ) {
    @PrimaryKey(autoGenerate = true)
    var id=0
}