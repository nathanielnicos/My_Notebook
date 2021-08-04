package id.nns.myjetpackcompose.compose

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.nns.myjetpackcompose.data.Note

@ExperimentalMaterialApi
@Composable
fun TextCardList(context: Context, notes: List<Note>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        StaggeredVerticalGrid(
            modifier = Modifier
                .padding(4.dp)
        ) {
            notes.forEach { note ->
                TextCard(context, note)
            }
        }
    }
}