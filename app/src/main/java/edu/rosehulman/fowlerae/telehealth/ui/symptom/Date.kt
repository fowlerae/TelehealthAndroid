package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Date(var name : String = "") : Parcelable {
    var qualityOfSleep: Int? = null
    var lastTouched: Timestamp? = null

    @get:Exclude
    var id = ""


    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot): Date {
            val date = snapshot.toObject(Date::class.java)!!
            date.id = snapshot.id
            return date
        }
    }
}