package com.kkdev.notable.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kkdev.notable.ui.theme.AppTheme
import com.kkdev.notable.ui.theme.appLightblack

@Composable
fun CustomNotesView(
    NoteTitle: String,
    NoteContent: String,
    NoteDate: String,
    onClick: () -> Unit
){
    val shape = AppTheme.shape.container
    Box(
        modifier = Modifier
            .background(Color.White, shape)
            .border(
                1.dp,
                AppTheme.colorScheme.onPrimary,
                shape,
            ) // Add a 1dp stroke with the same shape
            .padding(16.dp)
            .clickable(onClick = onClick)


        ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = NoteTitle,
                style = AppTheme.typography.labelLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = NoteContent,
                style = AppTheme.typography.labelNormal,
                maxLines = 2,
                color = appLightblack
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = NoteDate,
                style = AppTheme.typography.labelSmall,
                maxLines = 1,
                color = appLightblack
            )
        }



    }
}