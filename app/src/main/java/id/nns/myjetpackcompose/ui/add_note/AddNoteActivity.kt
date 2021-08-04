package id.nns.myjetpackcompose.ui.add_note

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import id.nns.myjetpackcompose.R
import id.nns.myjetpackcompose.compose.OutlinedTF
import id.nns.myjetpackcompose.compose.UnderlinedTF
import id.nns.myjetpackcompose.viewmodel.ViewModelFactory

class AddNoteActivity : ComponentActivity() {

    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowUI()
        }

        val factory = ViewModelFactory.getInstance()
        addNoteViewModel = ViewModelProvider(this, factory).get(AddNoteViewModel::class.java)

        observeValue()
    }

    private fun observeValue() {
        addNoteViewModel.isSaved.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    @Composable
    private fun ShowUI() {
        var title by remember {
            mutableStateOf("")
        }
        var content by remember {
            mutableStateOf("")
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Add Note",
                            color = Color.White
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                when {
                                    title.isBlank() -> {
                                        Toast.makeText(
                                            this@AddNoteActivity,
                                            "Title is still empty!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    content.isBlank() -> {
                                        Toast.makeText(
                                            this@AddNoteActivity,
                                            "Content is still empty!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else -> addNoteViewModel.saveNote(title.trim(), content.trim())
                                }
                            },
                            content = {
                                Icon(
                                    Icons.Filled.Check,
                                    "Save Note",
                                    tint = Color.White
                                )
                            }
                        )
                    },
                    backgroundColor = colorResource(id = R.color.dark_blue)
                )
            },
            backgroundColor = colorResource(id = R.color.light_gray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                UnderlinedTF(
                    title,
                    backgroundColor = colorResource(id = R.color.light_gray),
                    focusedColor = colorResource(id = R.color.dark_blue)
                ) { dataUTF ->
                    title = dataUTF
                }
                OutlinedTF(
                    content,
                    colorResource(id = R.color.dark_blue)
                ) { dataOTF ->
                    content = dataOTF
                }
            }
        }
    }

}