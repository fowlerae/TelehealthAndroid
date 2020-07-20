package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import kotlinx.android.synthetic.main.dialog_add_quality_of_sleep.view.*
import kotlinx.android.synthetic.main.fragment_symptom_list.view.*

private const val ARG_DATE = "date"

class SymptomListFragment : Fragment(), SymptomListAdapter.OnSymptomListener {
    lateinit var date: Date
    private lateinit var listAdapter: SymptomListAdapter
    private var listener: SymptomListAdapter.OnSymptomListener? = null
    lateinit var root: View
    private lateinit var listenerRegistration: ListenerRegistration
    private val dates = ArrayList<Date>()
    private val datesRef = FirebaseFirestore
        .getInstance()
        .collection("users")
        .document("UGSe2Si5KAsB0sQb9Gf7")
        .collection("dates")

    companion object {
        @JvmStatic
        fun newInstance(d: Date) =
            SymptomListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DATE, d)
                    date = d
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_symptom_list, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.symptom_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listAdapter = context?.let { SymptomListAdapter(it, this, date) }!!
        recyclerView.adapter = listAdapter
        listAdapter.addSnapshotListener()
        val dateTextView = root.findViewById<TextView>(R.id.date_fragment_text_view)
        dateTextView.text = date.name

        val addCardView: CardView? = root.findViewById(R.id.add_symptom_button_card)
        addCardView?.setOnClickListener {
            listAdapter.addSymptom()
        }

        val qualitySleepCardView: CardView =
            root.findViewById(R.id.add_quality_of_sleep_button_card)
        qualitySleepCardView.setOnClickListener {
            if (date.qualityOfSleep != null) {
                showAddEditDialog(1)
            } else {
                showAddEditDialog(-1)
            }
        }
        if (date.qualityOfSleep != null) {
            root.add_quality_of_sleep_text_view.text = "Quality of Sleep: ${date.qualityOfSleep}"
            root.add_quality_of_sleep_image_view.visibility = View.GONE
        }

        return root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = this
    }

    override fun onSymptomSelected(symptom: Symptom) {
        val fragment = SymptomFragment.newInstance(symptom, date)
        val ft: FragmentManager = parentFragmentManager
        ft.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("symptom")
            .commit()
        Log.d(Constants.TAG, "Adding symptom fragment")

    }

    override fun onAddSymptomSelected() {
        val fragment = AddSymptomFragment.newInstance(date)
        val ft: FragmentManager = parentFragmentManager
        ft.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("date")
            .commit()
        Log.d(Constants.TAG, "Adding add symptom fragment")

    }

    fun showAddEditDialog(position: Int) {
        // pos of -1 means add
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (position < 0) R.string.add_sleep_dialog_title else R.string.edit_sleep_dialog_title)
        val view =
            LayoutInflater.from(context).inflate(R.layout.dialog_add_quality_of_sleep, null, false)
        builder.setView(view)
        val docRef = datesRef.document("${date.name}")
        docRef.get().addOnSuccessListener { documentSnapshot ->
            date = Date.fromSnapshot(documentSnapshot)
        }
            if (date.qualityOfSleep != null) {
                view.quality_edit_text.setText(date.qualityOfSleep.toString())
            }
        builder.setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
            val quality = view.quality_edit_text.text.toString()
            date.qualityOfSleep = quality.toInt()
            datesRef.document("${date.name}").set(date)
            root.add_quality_of_sleep_text_view.text =
                "Quality of Sleep : ${date.qualityOfSleep.toString()}"
            root.add_quality_of_sleep_image_view.visibility = View.GONE
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