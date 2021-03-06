package edu.rosehulman.fowlerae.telehealth.ui.home

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Badge(var name: String = "") : Parcelable {
    @get:Exclude var id = ""
    var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"

        fun fromSnapshot(snapshot: DocumentSnapshot): Badge {
            val badge = snapshot.toObject(Badge::class.java)!!
            badge.id = snapshot.id
            return badge
        }
    }

}
