package edu.rosehulman.fowlerae.telehealth.ui.medication

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
import kotlinx.android.synthetic.main.dialog_add_medication.view.*

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
        medications.add(0, medication)
        medicationRef.add(medication)
    }

    private fun edit(medication: Medication, position: Int) {
        this.medications[position] = medication
        medicationRef.document(this.medications[position].id).set(this.medications[position])
    }

    private fun delete(position: Int) {
        medicationRef.document(medications[position].id).delete()
    }

    fun showAddEditDialog(position: Int) {
        // pos of -1 means add
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (position < 0) R.string.add_dialog_title else R.string.edit_dialog_title)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_medication, null, false)
        builder.setView(view)
        if (position >= 0) {
            view.name_edit_text.setText(medications[position].name)
            view.dosage_edit_text.setText(medications[position].dosage.toString())
            view.frequency_edit_text.setText(medications[position].frequency.toString())
            view.description_edit_text.setText(medications[position].description)
            view.shape_edit_text.setText(medications[position].shape)
            view.color_edit_text.setText(medications[position].color)
            view.prescribed_edit_text.setText(medications[position].prescribed.toString())
            view.time_edit_text.setText(medications[position].time)

        }
        builder.setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
            val color = view.color_edit_text.text.toString()
            val description = view.description_edit_text.text.toString()
            val dosage: Double = view.dosage_edit_text.text.toString().toDouble()
            val frequency: Int = view.frequency_edit_text.text.toString().toInt()
            val frequency_interval = view.frequency_edit_text.text.toString()
            val name = view.name_edit_text.text.toString()
            val time = view.time_edit_text.text.toString()
            var prescribed: Boolean = false
            if (view.prescribed_edit_text.text.toString() == "Yes") {
                prescribed = true
            }
            val shape = view.shape_edit_text.text.toString()
            val medication = Medication(
                color,
                description,
                dosage,
                frequency,
                frequency_interval,
                name,
                time,
                prescribed,
                shape
            )
            if (position < 0) {
                add(medication)
            } else {
                edit(medication, position)
            }
        }
        if (position >= 0) {
            builder.setNeutralButton("Delete") { _, _ ->
                delete(position)

            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }


}