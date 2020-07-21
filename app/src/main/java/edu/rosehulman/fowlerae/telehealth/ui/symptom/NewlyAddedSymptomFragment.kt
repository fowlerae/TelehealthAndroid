package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.fowlerae.telehealth.R

private const val ARG_SYMPTOM = "symptom"

class NewlyAddedSymptomFragment : Fragment() {

    lateinit var date: Date
    lateinit var symptom: Symptom

    companion object {
        @JvmStatic
        fun newInstance(
            s: Symptom,
            d: Date
        ) =
            NewlyAddedSymptomFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SYMPTOM, s)
                    date = d
                    symptom = s
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_newly_added_symptom, container, false)
        val nameTextView: TextView =
            root.findViewById(R.id.newly_added_symptom_fragment_name_text_view)
        nameTextView.text = symptom.name
        val button: Button = root.findViewById(R.id.finish_reporting_symptom_button)
        button.setOnClickListener {
            val rating: EditText? = root.findViewById(R.id.pain_rating_text_view)
            if (rating?.text != null) {
                symptom.rating = rating?.text.toString().toInt()
            }
            val duration: EditText? = root.findViewById(R.id.duration_edit_text)
            if (duration?.text != null) {
                symptom.duration = duration?.text.toString().toInt()
            }
            val description: EditText? = root.findViewById(R.id.description_text_ivew)
            if (description?.text != null) {
                symptom.description = description?.text.toString()
            }
            finishAddingSymptom()
        }
        return root
    }

    private fun finishAddingSymptom() {
        val symptomsRef = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document("UGSe2Si5KAsB0sQb9Gf7")
            .collection("dates")
            .document(date.name)
            .collection("symptoms")
        symptomsRef.add(symptom)
        val fragment: SymptomListFragment = SymptomListFragment.newInstance(date)
        val fragmentManager: FragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("date")
            .commit()
    }

}