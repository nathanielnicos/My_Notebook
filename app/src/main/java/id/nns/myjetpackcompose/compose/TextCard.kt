package id.nns.myjetpackcompose.compose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.nns.myjetpackcompose.data.Note
import id.nns.myjetpackcompose.ui.edit_note.EditNoteActivity

@ExperimentalMaterialApi
@Composable
fun TextCard(context: Context, note: Note) {
    Card(
        onClick = {
            Intent(context, EditNoteActivity::class.java).also {
                it.putExtra(EditNoteActivity.NOTE_KEY, note)
                context.startActivity(it)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Text(
                text = note.title,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = note.content,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            )
        }
    }
}