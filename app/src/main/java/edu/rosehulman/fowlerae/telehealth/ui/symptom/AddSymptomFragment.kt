package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.R

private const val ARG_SYMPTOM = "symptom"

class AddSymptomFragment() : Fragment() {
    private lateinit var adapter: AddSymptomAdapter

    companion object {
        @JvmStatic
        fun newInstance(symptom: Symptom) =
            AddSymptomFragment().apply {
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
        val root = inflater.inflate(R.layout.fragment_add_symptom, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.add_symptom_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { AddSymptomAdapter(it) }!!
        recyclerView.adapter = adapter
        adapter.addSnapshotListener()
        return root
    }
}