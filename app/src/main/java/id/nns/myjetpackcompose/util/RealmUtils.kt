package id.nns.myjetpackcompose.util

import id.nns.myjetpackcompose.data.Note
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class RealmUtils {

    companion object {
        @Volatile
        private var instance: RealmUtils? = null

        fun getInstance() : RealmUtils =
            instance ?: synchronized(this) {
                RealmUtils().apply {
                    instance = this
                }
            }
    }

    private val realmInstance: Realm by lazy {
        Realm.getInstance(RealmConfiguration
            .Builder()
            .name("my_notebook")
            .build()
        )
    }

    fun insertNote(title: String, content: String) {
        realmInstance.executeTransactionAsync { transactionRealm ->
            val note = Note()
            val currentId = transactionRealm.where<Note>().max("id")
            note.id = if (currentId == null) {
                1
            } else {
                currentId.toInt() + 1
            }
            note.title = title
            note.content = content

            transactionRealm.insert(note)
        }
    }

    fun getAllNotes() : List<Note> {
        val notesList = ArrayList<Note>()
        val notesFromRealm = realmInstance.where<Note>().findAllAsync()

        notesFromRealm.addChangeListener(RealmChangeListener {
            it.forEach { note ->
                notesList.add(note)
            }
        })

        return notesList
    }

    fun updateNote(id: Int?, title: String, content: String) {
        realmInstance.executeTransactionAsync { transactionRealm ->
            val selectedNote: Note? = transactionRealm
                .where<Note>()
                .equalTo("id", id)
                .findFirst()
            selectedNote?.title = title
            selectedNote?.content = content
        }
    }

    fun deleteNote(id: Int?) {
        realmInstance.executeTransactionAsync { transactionRealm ->
            val selectedNote: Note? = transactionRealm
                .where<Note>()
                .equalTo("id", id)
                .findFirst()
            selectedNote?.deleteFromRealm()
        }
    }

    fun deleteAllNotes(){
        realmInstance.executeTransactionAsync { transactionRealm ->
            val notes = transactionRealm.where<Note>().findAll()
            notes.deleteAllFromRealm()
        }
    }

}