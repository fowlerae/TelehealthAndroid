package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class Symptom(var name: String = "") : Parcelable {
    @get:Exclude
    var id = ""
    var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot) : Symptom {
            val symptom = snapshot.toObject(Symptom::class.java)!!
            symptom.id = snapshot.id
            return symptom
        }
    }
}