package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Symptom(var name: String = "", var rating: Int = 0, var date: String = "July 19, 2020") :
    Parcelable {
    @get:Exclude
    var id = ""
    var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot): Symptom {
            val symptom = snapshot.toObject(Symptom::class.java)!!
            symptom.id = snapshot.id
            return symptom
        }
    }
}