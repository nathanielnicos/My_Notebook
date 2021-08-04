package id.nns.myjetpackcompose.ui.edit_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.nns.myjetpackcompose.util.RealmUtils

class EditNoteViewModel(private val realmUtils: RealmUtils) : ViewModel() {

    private val _isFinish = MutableLiveData<Boolean>()
    val isFinish: LiveData<Boolean> get() = _isFinish

    fun editNote(id: Int?, title: String, content: String) {
        realmUtils.updateNote(id, title, content)
        _isFinish.value = true
    }

    fun deleteNote(id: Int?) {
        realmUtils.deleteNote(id)
        _isFinish.value = true
    }

}