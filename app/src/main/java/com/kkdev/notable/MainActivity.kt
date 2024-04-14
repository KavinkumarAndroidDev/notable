package com.kkdev.notable

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.kkdev.notable.data.NotesDatabase
import com.kkdev.notable.data.model.Notes
import com.kkdev.notable.navigation.NavigationItem
import com.kkdev.notable.screens.NotesAddScreen
import com.kkdev.notable.screens.NotesScreen
import com.kkdev.notable.screens.NotesViewScreen
import com.kkdev.notable.screens.SettingScreen
import com.kkdev.notable.ui.theme.AppTheme
import com.kkdev.notable.viewModel.NotesViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }
    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(db.dao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val state by viewModel.state.collectAsState()
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                NavHost(
                    navController = navController, 
                    startDestination = NavigationItem.Notes.route
                ) {
                    composable(NavigationItem.Notes.route){
                        NotesScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }
                    composable(NavigationItem.Settings.route){
                        SettingScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }
                    composable(NavigationItem.AddNote.route){
                        NotesAddScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }


                    composable("${NavigationItem.ViewNote.route}/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")
                        val selectedNote = state.notes.find { it.id.toString() == noteId }
                        NotesViewScreen(
                            navController = navController,
                            selectedNote = selectedNote
                        )
                    }
                }
            }
        }
    }
}

object ParcelableUtils {
    fun writeToByteArray(parcelable: Parcelable): ByteArray {
        val parcel = Parcel.obtain()
        parcelable.writeToParcel(parcel, 0)
        val bytes = parcel.marshall()
        parcel.recycle()
        return bytes
    }

    fun <T : Parcelable> readFromByteArray(bytes: ByteArray, clazz: Class<T>): T? {
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        return clazz.getConstructor(Parcel::class.java).newInstance(parcel)
    }
}

//use dependency injection for data modules and db implementation