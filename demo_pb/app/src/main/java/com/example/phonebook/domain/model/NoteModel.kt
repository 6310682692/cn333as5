package com.example.phonebook.domain.model

const val NEW_NOTE_ID = -1L

data class NoteModel(
    val id: Long = NEW_NOTE_ID, // This value is used for new notes
    val title: String = "",
    val content: String = "",
    val color: ColorModel = ColorModel.DEFAULT,
    val tag: TagModel = TagModel.DEFAULT
)