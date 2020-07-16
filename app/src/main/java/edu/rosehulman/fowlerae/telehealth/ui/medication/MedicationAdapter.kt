package edu.rosehulman.fowlerae.telehealth.ui.medication

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.*
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R

class MedicationAdapter(val context: Context) : RecyclerView.Adapter<MedicationViewHolder>() {
    private val medications = ArrayList<Medication>()
    private val medicationRef = FirebaseFirestore
        .getInstance()
        .collection("users")
        .document("UGSe2Si5KAsB0sQb9Gf7")
        .collection("medications")
    private lateinit var listenerRegistration: ListenerRegistration
    init {
        Log.d(Constants.TAG, "medicationRef: ${medicationRef.get()}")
        Log.d(Constants.TAG, "Size: ${medications.size}")
      //  add(Medication("please im sad"))

    }

    fun addSnapshotListener() {
        listenerRegistration = medicationRef
            .orderBy(Medication.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
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
            val project = Medication.fromSnapshot(documentChange.document)
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    Log.d(Constants.TAG, "Adding $project")
                    medications.add(0, project)
                    notifyItemInserted(0)
                    Log.d(Constants.TAG,"Size: ${medications.size}")
                }
                DocumentChange.Type.REMOVED -> {
                    Log.d(Constants.TAG, "Removing $project")
                    val index = medications.indexOfFirst { it.id == project.id }
                   medications.removeAt(index)
                    notifyItemRemoved(index)
                }
                DocumentChange.Type.MODIFIED -> {
                    Log.d(Constants.TAG, "Modifying $project")
                    val index = medications.indexOfFirst { it.id == project.id }
                    medications[index] = project
                    notifyItemChanged(index)
                }
            }
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, index: Int): MedicationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.medication_card_view, parent, false)
        return MedicationViewHolder(view, this)
    }

    override fun onBindViewHolder(
        listViewHolder: MedicationViewHolder,
        index: Int
    ) {
        listViewHolder.bind(medications[index])
    }

    override fun getItemCount() = medications.size

    fun add(medication: Medication) {
        medications.add(0,medication)
        medicationRef.add(medication)
    }

    private fun edit(badges: String, position: Int) {
        this.medications[position].name = badges
        medicationRef.document(this.medications[position].id).set(this.medications[position])
    }

    private fun delete(position: Int) {
        medicationRef.document(medications[position].id).delete()
    }


}