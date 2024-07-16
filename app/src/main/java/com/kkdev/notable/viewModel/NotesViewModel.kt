package com.kkdev.notable.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkdev.notable.data.NotesEvent
import com.kkdev.notable.data.NotesState
import com.kkdev.notable.data.SortType
import com.kkdev.notable.data.daos.NotesDao
import com.kkdev.notable.data.model.Notes
import com.kkdev.notable.repository.SettingsPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)class NotesViewModel(
    private val dao: NotesDao,
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NEWEST_FIRST)

    init {
        viewModelScope.launch {
            settingsPreferences.getSortType().collect { sortType ->
                _sortType.value = sortType
            }
        }
    }

    private val _notes = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.NOTES_TITLE -> dao.getNotesOrderedByTitle()
                SortType.NEWEST_FIRST -> dao.getNewNotes()
                SortType.OLDEST_FIRST -> dao.getOldNotes()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NotesState())

    val state = combine(_state, _sortType, _notes) { state, sortType, notes ->
        state.copy(
            notes = notes,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesState())


    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.deleteNotes -> {
                viewModelScope.launch {
                    dao.deleteNotes(event.notes)
                }
            }

            NotesEvent.SaveNotes -> {
                val notesTitle = state.value.notesTitle
                val notesContent = state.value.notesContent
                val lastedited = getFormattedTime()

                if (notesContent.isBlank()) {
                    return
                }

                val notes = Notes(
                    noteContent = notesContent,
                    noteTitle = notesTitle,
                    lastEdited = lastedited
                )
                viewModelScope.launch {
                    dao.upsertNotes(notes)
                }
                _state.update {
                    it.copy(
                        notesContent = "",
                        notesTitle = "",
                        lastEdited = ""
                    )
                }
            }

            is NotesEvent.SortNotes -> {
                viewModelScope.launch {
                    settingsPreferences.saveSortType(event.sortType)
                    _sortType.value = event.sortType
                }
            }

            is NotesEvent.setContent -> {
                _state.update {
                    it.copy(
                        notesContent = event.notesContent
                    )
                }
            }

            is NotesEvent.setTitle -> {
                _state.update {
                    it.copy(
                        notesTitle = event.notesTitle
                    )
                }
            }

            is NotesEvent.lastEdited -> {
                _state.update {
                    it.copy(
                        notesTitle = event.lastEdited
                    )
                }
            }

            NotesEvent.DiscardNotes -> {
                _state.update {
                    it.copy(
                        notesContent = "",
                        notesTitle = "",
                        lastEdited = ""
                    )
                }
            }

            NotesEvent.HideAlertDialog -> {
                _state.update {
                    it.copy(
                        isShowingAlert = false
                    )
                }
            }
            NotesEvent.showAlertDialog -> {
                _state.update {
                    it.copy(
                        isShowingAlert = true
                    )
                }
            }

            NotesEvent.HideSortTypeDialog -> {
                _state.update {
                    it.copy(
                        isShowingSortType = false
                    )
                }
            }
            NotesEvent.showSortTypeDialog -> {
                _state.update {
                    it.copy(
                        isShowingSortType = true
                    )
                }
            }
        }
    }
    fun checkAvailability(){
        if (state.value.notes.isEmpty()){
            _state.update {
                it.copy(
                    noteAvailable = true
                )
            }
        }else{
            _state.update {
                it.copy(
                    noteAvailable = false
                )
            }
        }

    }
}

private fun getFormattedTime(): String {
    val currentTime = System.currentTimeMillis()
    val sdf = SimpleDateFormat("MMMM dd, HH:mm", Locale.getDefault())
    val formattedTime = sdf.format(Date(currentTime))
    return formattedTime
}
