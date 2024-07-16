package com.kkdev.notable.data

import com.kkdev.notable.data.model.Notes

data class NotesState (
    val notes: List<Notes> = emptyList(),
    val notesTitle: String = "",
    val notesContent: String = "",
    val lastEdited: String = "",
    val isShowingAlert: Boolean = false,
    val isShowingSortType: Boolean = false,
    val sortType: SortType = SortType.NEWEST_FIRST,
    val noteAvailable: Boolean = false
)