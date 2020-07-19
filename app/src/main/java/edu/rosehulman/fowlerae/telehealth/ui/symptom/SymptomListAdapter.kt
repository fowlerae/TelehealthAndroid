package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.dialog_add_quality_of_sleep.view.*
import kotlinx.android.synthetic.main.fragment_symptom_list.view.*

class SymptomListAdapter(
    val context: Context,
    val listener: OnSymptomListener,
    val date: Date
) :
    RecyclerView.Adapter<SymptomListViewHolder>() {
    private val symptoms = ArrayList<Symptom>()
    private val symptomsRef = FirebaseFirestore
        .getInstance()
        .collection("users")
        .document("UGSe2Si5KAsB0sQb9Gf7")
        .collection("dates")
        .document("${date.name}")
        .collection("symptoms")
    private lateinit var listenerRegistration: ListenerRegistration

    fun addSnapshotListener() {
        listenerRegistration = symptomsRef
            .orderBy(Symptom.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "listen error", e)
                } else {
                    processSnapshotChanges(querySnapshot!!)
                }
            }
    }


    private fun processSnapshotChanges(querySnapshot: QuerySnapshot) {
        // Snapshots has documents and documentChanges which are flagged by type,
        // so we can handle C,U,D differently.
        for (documentChange in querySnapshot.documentChanges) {
            val symptom = Symptom.fromSnapshot(documentChange.document)
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    Log.d(Constants.TAG, "Adding $symptom")
                    symptoms.add(0, symptom)
                    notifyItemInserted(0)
                }
                DocumentChange.Type.REMOVED -> {
                    Log.d(Constants.TAG, "Removing $symptom")
                    val index = symptoms.indexOfFirst { it.id == symptom.id }
                    symptoms.removeAt(index)
                    notifyItemRemoved(index)
                }
                DocumentChange.Type.MODIFIED -> {
                    Log.d(Constants.TAG, "Modifying $symptom")
                    val index = symptoms.indexOfFirst { it.id == symptom.id }
                    symptoms[index] = symptom
                    notifyItemChanged(index)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): SymptomListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.symptom_card_view, parent, false)
        return SymptomListViewHolder(view, this)
    }

    override fun onBindViewHolder(
        listViewHolder: SymptomListViewHolder,
        index: Int
    ) {
        listViewHolder.bind(symptoms[index])
    }

    override fun getItemCount() = symptoms.size

    fun add(symptom: Symptom) {
        symptomsRef.add(symptom)
    }

    private fun edit(symptom: String, position: Int) {
        this.symptoms[position].name = symptom
        symptomsRef.document(this.symptoms[position].id).set(this.symptoms[position])
    }

    private fun delete(position: Int) {
        symptomsRef.document(symptoms[position].id).delete()
    }

    fun selectSymptom(position: Int) {
        //  this.symptoms[position]
        listener.onSymptomSelected(this.symptoms[position])

    }

    fun addSymptom() {
        listener.onAddSymptomSelected()
    }

    interface OnSymptomListener {
        fun onSymptomSelected(symptom: Symptom)
        fun onAddSymptomSelected()

    }

    fun showAddEditDialog(position: Int) {
        // pos of -1 means add
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (position < 0) R.string.add_sleep_dialog_title else R.string.edit_sleep_dialog_title)
        val view =
            LayoutInflater.from(context).inflate(R.layout.dialog_add_quality_of_sleep, null, false)
        builder.setView(view)
        if (position >= 0) {
            if (date.qualityOfSleep != null) {
                view.quality_edit_text.setText(date.qualityOfSleep.toString())
            }


        }
        builder.setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
            val quality: String? = view.add_quality_of_sleep_text_view.text.toString()
            if (quality != null) {
                date.qualityOfSleep = quality.toInt()
            }
        }
//        if (position >= 0) {
//            builder.setNeutralButton("Delete") { _, _ ->
//                delete(position)
//
//            }
//        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }


}