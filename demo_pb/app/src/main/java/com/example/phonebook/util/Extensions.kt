package com.example.phonebook.util

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}
