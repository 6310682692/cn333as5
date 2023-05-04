package com.example.phonebook.domain.model

import com.example.phonebook.database.ColorDbModel

data class ColorModel(
    val id: Long,
    val name: String,
    val hex: String
) {
    companion object {
        val DEFAULT = with(ColorDbModel.DEFAULT_COLOR) { ColorModel(id, name, hex) }
    }
}
