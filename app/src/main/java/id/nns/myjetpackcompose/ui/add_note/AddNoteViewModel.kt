package id.nns.myjetpackcompose.ui.add_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.nns.myjetpackcompose.util.RealmUtils

class AddNoteViewModel(private val realmUtils: RealmUtils) : ViewModel() {

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> get() = _isSaved

    fun saveNote(title: String, content: String) {
        realmUtils.insertNote(title, content)
        _isSaved.value = true
    }

}