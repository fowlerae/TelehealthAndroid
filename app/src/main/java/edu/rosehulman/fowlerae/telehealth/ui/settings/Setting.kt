package edu.rosehulman.fowlerae.telehealth.ui.settings

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Setting(var name: String = "", var imageResource: String = "") : Parcelable {
    @get:Exclude
    var id = ""

    @ServerTimestamp
    var created: Timestamp? = null

    companion object {
        const val CREATED_KEY = "created"

        fun fromSnapshot(snapshot: DocumentSnapshot): Setting {
            val setting = snapshot.toObject(Setting::class.java)!!
            setting.id = snapshot.id
            return setting
        }
    }

}
