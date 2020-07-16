package edu.rosehulman.fowlerae.telehealth.ui.medication

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Medication(var name: String = "") : Parcelable {
    @get:Exclude var id = ""
    @ServerTimestamp
    var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"

        fun fromSnapshot(snapshot: DocumentSnapshot): Medication {
            val project = snapshot.toObject(Medication::class.java)!!
            project.id = snapshot.id
            return project
        }
    }

}
