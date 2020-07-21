package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
        return root
    }
}