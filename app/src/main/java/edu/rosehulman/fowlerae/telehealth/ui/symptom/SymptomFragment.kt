package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.fowlerae.telehealth.R

private const val ARG_SYMPTOM = "symptom"

class SymptomFragment() : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(symptom: Symptom) =
            SymptomFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SYMPTOM, symptom)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_symptom, container, false)
        return root
    }
}