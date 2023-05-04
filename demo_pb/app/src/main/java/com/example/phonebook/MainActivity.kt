package com.example.phonebook

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonebook.routing.MyNotesRouter
import com.example.phonebook.routing.Screen
import com.example.phonebook.screens.NotesScreen
import com.example.phonebook.screens.SaveNoteScreen
import com.example.phonebook.viewmodel.MainViewModel
import com.example.phonebook.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                MainActivityScreen(viewModel)

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainActivityScreen(viewModel: MainViewModel) {
    Surface {
        when (MyNotesRouter.currentScreen) {
            is Screen.Notes -> NotesScreen(viewModel)
            is Screen.SaveNote -> SaveNoteScreen(viewModel)
        }
    }
}
