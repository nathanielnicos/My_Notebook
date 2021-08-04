package id.nns.myjetpackcompose.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import id.nns.myjetpackcompose.R
import id.nns.myjetpackcompose.compose.TextCardList
import id.nns.myjetpackcompose.data.Note
import id.nns.myjetpackcompose.ui.add_note.AddNoteActivity
import id.nns.myjetpackcompose.viewmodel.ViewModelFactory

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowUI()
        }

        val factory = ViewModelFactory.getInstance()
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    @Composable
    private fun ShowUI() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = resources.getString(R.string.app_name),
                            color = Color.White
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                reload()
                            },
                            content = {
                                Icon(
                                    Icons.Filled.Refresh,
                                    "Refresh Note",
                                    tint = Color.White
                                )
                            }
                        )
                        IconButton(
                            onClick = {
                                mainViewModel.deleteAllNotes()
                                reload()
                            },
                            content = {
                                Icon(
                                    Icons.Filled.Delete,
                                    "Save Note",
                                    tint = Color.White
                                )
                            }
                        )
                    },
                    backgroundColor = colorResource(id = R.color.dark_blue)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        goToAddNoteActivity()
                    },
                    backgroundColor = colorResource(id = R.color.dark_blue),
                    contentColor = Color.White,
                    content = {
                        Icon(
                            Icons.Filled.Add,
                            "Add Note"
                        )
                    }
                )
            },
            backgroundColor = colorResource(id = R.color.light_gray)
        ) {
            NotesLiveData(notesLiveData = mainViewModel.notes)
        }
    }

    private fun goToAddNoteActivity() {
        Intent(
            this@MainActivity,
            AddNoteActivity::class.java
        ).also {
            startActivity(it)
        }
    }

    @Composable
    private fun NotesLiveData(notesLiveData: LiveData<List<Note>>) {
        val notesList by notesLiveData.observeAsState(initial = emptyList())

        if (notesList.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Empty",
                )
            }
        } else {
            TextCardList(this@MainActivity, notesList)
        }
    }

    private fun reload() {
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0,0)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getAllNote()
    }

}