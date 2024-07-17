package com.kkdev.notable.data

import com.kkdev.notable.data.model.Notes

sealed interface NotesEvent {
    object SaveNotes: NotesEvent
    object DiscardNotes: NotesEvent
    object updateDA : NotesEvent
    data class setTitle(val notesTitle: String): NotesEvent
    data class setContent(val notesContent: String): NotesEvent
    data class lastEdited(val lastEdited: String): NotesEvent
    object showAlertDialog: NotesEvent
    object HideAlertDialog: NotesEvent
    object showSortTypeDialog: NotesEvent
    object HideSortTypeDialog: NotesEvent
    data class SortNotes(val sortType: SortType): NotesEvent
    data class deleteNotes(val notes: Notes): NotesEvent
}
