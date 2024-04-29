@file:OptIn(ExperimentalMaterial3Api::class)

package com.kkdev.notable.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkdev.notable.composables.CustomFab
import com.kkdev.notable.composables.CustomNotesView
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.data.NotesState
import com.kkdev.notable.navigation.NavigationItem
import com.kkdev.notable.ui.theme.AppTheme
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
    navController: NavController
){
    Scaffold (
        floatingActionButton = {
            CustomFab(onClick = {
                navController.navigate(NavigationItem.AddNote.route)
            })
        },
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Notable",
                    style = AppTheme.typography.titleNormal)
               },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search the notes"
                        )
                    }
                    IconButton(onClick = {navController.navigate(NavigationItem.Settings.route)}) {
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
                    }
                ) { notes ->
                    CustomNotesView(
                        NoteTitle = notes.noteTitle,
                        NoteContent = notes.noteContent,
                        NoteDate = notes.lastEdited,
                        onClick = {
                            val route = "${NavigationItem.ViewNote.route}/${notes.id}" // Pass the note ID to the route
                            navController.navigate(route)
                        }
                    )
                }

            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = AppTheme.shape.container,
        ) {
            SwipeToDismiss(
                state = state,
                background = {
                    DeleteBackground(swipeDismissState = state)
                },
                dismissContent = { content(item) },
                directions = setOf(DismissDirection.EndToStart)
            )
        }

    }
}

@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        AppTheme.colorScheme.error
    } else Color.Transparent
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = AppTheme.colorScheme.onError
        )
    }
}
