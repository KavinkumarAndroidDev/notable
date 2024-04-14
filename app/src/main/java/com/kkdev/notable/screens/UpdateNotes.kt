package com.kkdev.notable.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.data.NotesState
import com.kkdev.notable.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNote(
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
    navController: NavController,
    noteTitle: String,
    noteContent: String


){

    Scaffold(
        topBar = {
            TopAppBar(title = { /*TODO*/ },
                actions ={
                    IconButton(
                        onClick = {
                            onEvent(NotesEvent.SaveNotes)
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Search the notes"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = Modifier.padding(16.dp)


    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            BasicTextField(
                value = noteTitle,
                onValueChange = {it ->
                    onEvent(NotesEvent.setTitle(it))},
                textStyle = AppTheme.typography.titleLarge,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .heightIn(min = 56.dp)
                    .background(Color.Transparent)
                    .border(0.dp, Color.Transparent)

            )

            BasicTextField(
                value = noteContent,
                onValueChange = {it ->
                    onEvent(NotesEvent.setContent(it))},
                textStyle = AppTheme.typography.titleNormal,
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .heightIn(min = 56.dp)
                    .background(Color.Transparent)
                    .border(0.dp, Color.Transparent)
            )
        }
    }
}
