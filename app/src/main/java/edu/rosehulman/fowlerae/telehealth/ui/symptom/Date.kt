package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class Date(var name : String = "") : Parcelable {
    @get:Exclude
    var id = ""
    var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot) : Date{
            val date = snapshot.toObject(Date::class.java)!!
            date.id = snapshot.id
            return date
        }
    }
}