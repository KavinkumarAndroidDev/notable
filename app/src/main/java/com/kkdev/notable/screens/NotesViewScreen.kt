package com.kkdev.notable.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkdev.notable.data.model.Notes
import com.kkdev.notable.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesViewScreen(
    selectedNote: Notes?,
    navController: NavController,
){
    Scaffold(
            topBar = {
                TopAppBar(title = {

                    Text(
                        text = if(selectedNote?.noteTitle?.isBlank() == true){
                            "notable"
                        } else {
                            selectedNote?.noteTitle ?: ""
                        },
                        style = AppTheme.typography.labelLarge,
                        maxLines = 1
                    )


                },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            modifier = Modifier.padding(16.dp)
        ) {pad ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
            )
            {
                Text(
                    text = selectedNote?.noteContent ?: "",
                    style = AppTheme.typography.noteContent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .heightIn(min = 56.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }

}