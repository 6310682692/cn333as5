package com.example.phonebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "nameTag") val nameTag: String
) {
    companion object {
        val DEFAULT_TAGS = listOf(
            TagDbModel(1, "General"),
            TagDbModel(2, "School"),
            TagDbModel(3, "Home"),
            TagDbModel(4, "Work"),

        )
        val DEFAULT_TAG = DEFAULT_TAGS[0]
    }
}