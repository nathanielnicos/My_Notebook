package id.nns.myjetpackcompose.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import id.nns.myjetpackcompose.R

@Composable
fun DialogScreen(
    isShowDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    onConfirmClick: () -> Unit
) {
    if (isShowDialog) {
        AlertDialog(
            onDismissRequest = {
                setShowDialog(false)
            },
            title = {
                Text(
                    text = "Delete Note"
                )
            },
            text = {
                Text(
                    text = "Are you sure?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmClick()
                    },
                    content = {
                        Text(
                            text = "Yes",
                            color = Color.Red
                        )
                    }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    content = {
                        Text(
                            text = "No",
                            color = colorResource(id = R.color.dark_blue)
                        )
                    }
                )
            }
        )
    }
}