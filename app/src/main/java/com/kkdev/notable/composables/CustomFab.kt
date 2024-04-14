package com.kkdev.notable.composables

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.kkdev.notable.ui.theme.AppTheme

@Composable
fun CustomFab(
    onClick: () -> Unit
){
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor =  AppTheme.colorScheme.onPrimary
    ) {
        Icon(imageVector = Icons.Outlined.Create, contentDescription = "Add new notes", tint = AppTheme.colorScheme.primary)
    }
}