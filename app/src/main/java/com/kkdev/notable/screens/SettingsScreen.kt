package com.kkdev.notable.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkdev.notable.composables.CustomAppBar
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.data.NotesState
import com.kkdev.notable.data.SortType
import com.kkdev.notable.ui.theme.AppTheme


@Composable
fun SettingScreen(
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CustomAppBar(
                onBackPressed = { navController.popBackStack()},
                titleText = "Settings"
            )
        }
    ) {
        val context = LocalContext.current
        val sorttype = when (state.sortType) {
            SortType.NEWEST_FIRST -> "Sorted by newest first"
            SortType.OLDEST_FIRST-> "Sorted by oldest first"
            SortType.NOTES_TITLE -> "Sorted by Title"
        }
        if (state.isShowingSortType){
            SortTypeSettingMenu (
                onEvent = onEvent
            )
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)

        ){
            Text(text = "Recently deleted",
                style = AppTheme.typography.labelLarge,
                modifier = Modifier.padding(16.dp)
            )
            Column (modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onEvent(NotesEvent.showSortTypeDialog)
                }
                .fillMaxWidth()
            ){
                Text(text = "List order",
                    style = AppTheme.typography.labelLarge
                )
                Text(text = sorttype,
                    style = AppTheme.typography.labelNormal,
                    color = AppTheme.colorScheme.error
                )
            }
            Column (
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ){
                Text(text = "Build Version",
                    style = AppTheme.typography.labelLarge
                )
                Text(text = "1.0",
                    style = AppTheme.typography.labelNormal,
                    color = AppTheme.colorScheme.error
                )
            }
            Text(text = "About Developer",
                style = AppTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.linkedin.com/in/kavinkumar442005/")
                        )
                        context.startActivity(intent)
                    }
            )

        }
    }
}

@Composable
fun SortTypeSettingMenu(
    onEvent: (NotesEvent) -> Unit,
){
    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = { onEvent(NotesEvent.HideSortTypeDialog) },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                SortType.entries.forEach { sortType ->
                    Text(
                        text = when (sortType) {
                            SortType.NEWEST_FIRST -> "Sorted by newest first"
                            SortType.OLDEST_FIRST -> "Sorted by oldest first"
                            SortType.NOTES_TITLE -> "Sorted by Title"
                        },
                        style = AppTheme.typography.labelLarge,
                        color = AppTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .clickable {
                                onEvent(NotesEvent.SortNotes(sortType))
                                onEvent(NotesEvent.HideSortTypeDialog)
                            }
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    )
}