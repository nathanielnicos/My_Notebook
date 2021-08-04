package id.nns.myjetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.nns.myjetpackcompose.di.Injection
import id.nns.myjetpackcompose.ui.add_note.AddNoteViewModel
import id.nns.myjetpackcompose.ui.edit_note.EditNoteViewModel
import id.nns.myjetpackcompose.ui.main.MainViewModel
import id.nns.myjetpackcompose.util.RealmUtils

class ViewModelFactory private constructor(private val realmUtils: RealmUtils) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRealmUtils()).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(realmUtils) as T
            }
            modelClass.isAssignableFrom(AddNoteViewModel::class.java) -> {
                AddNoteViewModel(realmUtils) as T
            }
            modelClass.isAssignableFrom(EditNoteViewModel::class.java) -> {
                EditNoteViewModel(realmUtils) as T
            }
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}!")
        }
    }

}