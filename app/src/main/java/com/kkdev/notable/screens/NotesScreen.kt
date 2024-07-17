@file:OptIn(ExperimentalMaterial3Api::class)

package com.kkdev.notable.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkdev.notable.R
import com.kkdev.notable.composables.CustomFab
import com.kkdev.notable.composables.CustomNotesView
import com.kkdev.notable.composables.SwipeToDeleteContainer
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.data.NotesState
import com.kkdev.notable.navigation.NavigationItem
import com.kkdev.notable.ui.theme.AppTheme
import kotlinx.coroutines.delay


@Composable
fun NotesScreen(
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
    navController: NavController
) {
    LaunchedEffect(state.notes) {
        onEvent(NotesEvent.updateDA)
    }

    Scaffold(
        floatingActionButton = {
            CustomFab(onClick = {
                navController.navigate(NavigationItem.AddNote.route)
            })
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notable",
                        style = AppTheme.typography.titleNormal
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search the notes"
                        )
                    }
                    IconButton(onClick = { navController.navigate(NavigationItem.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Menu icon"
                        )
                    }
                }
            )
        },
        modifier = Modifier.padding(16.dp)
    ) {
        if (state.noteAvailable) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.notes, key = { it.id }) { notes ->
                    SwipeToDeleteContainer(
                        item = notes,
                        onDelete = {
                            onEvent(NotesEvent.deleteNotes(notes))
                        },
                        onClick = {
                            val route = "${NavigationItem.ViewNote.route}/${notes.id}" // Pass the note ID to the route
                            navController.navigate(route)
                        }
                    ) { notes ->
                        CustomNotesView(
                            NoteTitle = notes.noteTitle,
                            NoteContent = notes.noteContent,
                            NoteDate = notes.lastEdited
                        )
                    }
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.todo_svg),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                Text(
                    text = "Create your first notes...",
                    color = AppTheme.colorScheme.onPrimary,
                    style = AppTheme.typography.labelLarge
                )
            }
        }
    }
}




