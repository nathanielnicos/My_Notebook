package id.nns.myjetpackcompose.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.nns.myjetpackcompose.data.Note
import id.nns.myjetpackcompose.util.RealmUtils

class MainViewModel(private val realmUtils: RealmUtils) : ViewModel() {

    private var _notes = MutableLiveData<List<Note>>()
    val notes : LiveData<List<Note>> get() = _notes

    fun getAllNote() {
        _notes.value = realmUtils.getAllNotes()
    }

    fun deleteAllNotes() {
        realmUtils.deleteAllNotes()
    }

}