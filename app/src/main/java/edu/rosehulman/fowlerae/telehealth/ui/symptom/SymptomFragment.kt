package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.rosehulman.fowlerae.telehealth.R


private const val ARG_SYMPTOM = "symptom"

class SymptomFragment() : Fragment() {

    lateinit var date: Date
    lateinit var symptom: Symptom

    companion object {
        @JvmStatic
        fun newInstance(
            s: Symptom,
            d: Date
        ) =
            SymptomFragment().apply {
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
        val root = inflater.inflate(R.layout.fragment_symptom, container, false)

        val symptomName: TextView = root.findViewById(R.id.symptom_fragment_name_text_view)
        var string: String = "Symptom Name: ${symptom.name}"
        symptomName.text = string
        val painRating = root.findViewById<TextView>(R.id.pain_rating_text_view)
        string = "Symptom Pain Rating: ${symptom.rating}"
        painRating.text = string
        val symptomDescription = root.findViewById<TextView>(R.id.description_text_ivew)
        string = "Description: ${symptom.description}"
        symptomDescription.text = string
        val painDuration = root.findViewById<TextView>(R.id.pain_duration_text_view)
        string = "Symptom Duration: ${symptom.duration} minutes"
        painDuration.text = string
        return root
    }
}