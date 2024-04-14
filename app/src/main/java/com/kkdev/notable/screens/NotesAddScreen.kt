package com.kkdev.notable.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkdev.notable.composables.CustomAlertDialog
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.data.NotesState
import com.kkdev.notable.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAddScreen(
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
    navController: NavController

){
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    if(state.isShowingAlert){
        CustomAlertDialog(
            onEvent = onEvent,
            titleText = "Are you sure?",
            titleContent = "You will not be able to recover this note",
            confirmBtnTxt = "Yes, discard it",
            dissmissBtnTxt = "Cancel",
            onConfirm = {
                onEvent(NotesEvent.HideAlertDialog)
                navController.popBackStack()
                onEvent(NotesEvent.DiscardNotes)
            },
            onDissmiss = {
                onEvent(NotesEvent.HideAlertDialog)
            }
        )

    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "notable",style = AppTheme.typography.labelLarge)},
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
                    IconButton(onClick = {
                        if (state.notesTitle.isNotEmpty() || state.notesContent.isNotEmpty()) {
                            onEvent(NotesEvent.showAlertDialog)
                        } else {
                            navController.popBackStack()
                        }
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
    ) {

        BackHandler (true){
            if (state.notesTitle.isNotEmpty() || state.notesContent.isNotEmpty()) {
                onEvent(NotesEvent.showAlertDialog)
            } else {
                navController.popBackStack()
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            BorderlessTextField(
                value = state.notesTitle,
                txtStyle = AppTheme.typography.noteTitle,
                onValueChange = {it ->
                    onEvent(NotesEvent.setTitle(it))},
                placeholder =  "Title"
            )
            BasicTextField(
                value = state.notesContent,
                onValueChange = {it ->
                    onEvent(NotesEvent.setContent(it))},
                textStyle = AppTheme.typography.noteContent,
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .heightIn(min = 56.dp)
                    .focusRequester(focusRequester)
                    .verticalScroll(rememberScrollState())
            )
        }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            focusManager.moveFocus(FocusDirection.Down)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderlessTextField(
    modifier: Modifier = Modifier,
    value: String,
    txtStyle: TextStyle,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = txtStyle, // Change text color as per your requirement
        placeholder = { Text(text = placeholder, style = txtStyle) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        ),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent, // Make the background transparent
            focusedIndicatorColor = Color.Transparent, // Hide the focus indicator
            unfocusedIndicatorColor = Color.Transparent, // Hide the unfocused indicator
            cursorColor = Color.Black // Change cursor color as per your requirement
        )
    )
}