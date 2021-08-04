package id.nns.myjetpackcompose.ui.edit_note

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import id.nns.myjetpackcompose.R
import id.nns.myjetpackcompose.compose.DialogScreen
import id.nns.myjetpackcompose.compose.OutlinedTF
import id.nns.myjetpackcompose.compose.UnderlinedTF
import id.nns.myjetpackcompose.data.Note
import id.nns.myjetpackcompose.viewmodel.ViewModelFactory

class EditNoteActivity : ComponentActivity() {

    companion object {
        const val NOTE_KEY = "note_key"
    }

    private lateinit var editNoteViewModel: EditNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val note = intent.getParcelableExtra<Note>(NOTE_KEY)
            ShowUI(note)
        }

        val factory = ViewModelFactory.getInstance()
        editNoteViewModel = ViewModelProvider(this, factory).get(EditNoteViewModel::class.java)

        observeValue()
    }

    private fun observeValue() {
        editNoteViewModel.isFinish.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    @Composable
    private fun ShowUI(note: Note?) {
        var title by remember {
            mutableStateOf(note?.title ?: "")
        }
        var content by remember {
            mutableStateOf(note?.content ?: "")
        }
        val (isShowDialog, setShowDialog) = remember {
            mutableStateOf(false)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Edit Note",
                            color = Color.White
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
                ButtonSave(note?.id, title, content)
                ButtonDelete(setShowDialog)
                DialogScreen(isShowDialog, setShowDialog) {
                    editNoteViewModel.deleteNote(note?.id)
                }
            }
        }
    }

    @Composable
    private fun ButtonSave(id: Int?, title: String, content: String) {
        Button(
            onClick = {
                when {
                    title.isBlank() -> {
                        Toast.makeText(
                            this@EditNoteActivity,
                            "Title is still empty!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    content.isBlank() -> {
                        Toast.makeText(
                            this@EditNoteActivity,
                            "Content is still empty!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> editNoteViewModel.editNote(id, title.trim(), content.trim())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.dark_blue)
            ),
            content = {
                Text(
                    text = "Save",
                    color = Color.White
                )
            }
        )
    }

    @Composable
    private fun ButtonDelete(setShowDialog: (Boolean) -> Unit) {
        Button(
            onClick = {
                setShowDialog(true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            ),
            content = {
                Text(
                    text = "Delete",
                    color = Color.White
                )
            }
        )
    }

}