package com.example.phonebook.screens


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color


import com.example.phonebook.domain.model.NoteModel
import com.example.phonebook.ui.components.Note
import com.example.phonebook.viewmodel.MainViewModel

@ExperimentalMaterialApi
@Composable
fun NotesScreen(viewModel: MainViewModel) {
    val notes by viewModel.notesNotInTrash.observeAsState(listOf())
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.DarkGray,
                title = {
                    Text(
                        text = "Phone book",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
            )
        },

        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.Black,
                onClick = { viewModel.onCreateNewNoteClick() },
                contentColor = MaterialTheme.colors.background,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Note Button"
                    )
                }
            )
        }
    ) {
        if (notes.isNotEmpty()) {
            NotesList(
                notes = notes,
                onNoteClick = { viewModel.onNoteClick(it) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun NotesList(
    notes: List<NoteModel>,
    onNoteClick: (NoteModel) -> Unit
) {
    LazyColumn {
        items(count = notes.size) { noteIndex ->
            val note = notes[noteIndex]
            Note(
                note = note,
                onNoteClick = onNoteClick,
            )
        }
    }
}

