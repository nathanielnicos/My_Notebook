package id.nns.myjetpackcompose.data

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
open class Note(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var content: String = ""
) : RealmObject(), Parcelable