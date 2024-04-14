package com.kkdev.notable.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.ui.theme.AppTheme

@Composable
fun CustomAlertDialog(
    onEvent: (NotesEvent) -> Unit,
    titleText: String,
    titleContent: String,
    confirmBtnTxt: String,
    dissmissBtnTxt: String,
    onDissmiss: () -> Unit,
    onConfirm: () -> Unit
){
    AlertDialog(shape = RoundedCornerShape(20.dp),
        onDismissRequest = {
            onEvent(NotesEvent.HideAlertDialog)
        },
        text = { Text(text = titleContent, style = AppTheme.typography.alertText) },
        title = { Text(text = titleText, style = AppTheme.typography.alertTitle) },

        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppTheme.colorScheme.error,
                    containerColor = AppTheme.colorScheme.error
                )

            ) {
                Text(
                    text = confirmBtnTxt,
                    style = AppTheme.typography.labelNormal,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    color = AppTheme.colorScheme.primary
                )

            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDissmiss,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp),
                border = BorderStroke(1.dp, color = AppTheme.colorScheme.error),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppTheme.colorScheme.error)
            ) {
                Text(
                    text = dissmissBtnTxt,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    color = AppTheme.colorScheme.error,
                    style = AppTheme.typography.labelNormal
                )
            }
        }
    )
}