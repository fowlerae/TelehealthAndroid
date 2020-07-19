package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Date(var name : String = "") : Parcelable {
//    @get:Exclude
//    var id = ""
//    var lastTouched: Timestamp? = null
//
//    companion object {
//        const val LAST_TOUCHED_KEY = "lastTouched"
//        fun fromSnapshot(snapshot: DocumentSnapshot) : Date{
//            val date = snapshot.toObject(Date::class.java)!!
//            date.id = snapshot.id
//            return date
//        }
//    }
}