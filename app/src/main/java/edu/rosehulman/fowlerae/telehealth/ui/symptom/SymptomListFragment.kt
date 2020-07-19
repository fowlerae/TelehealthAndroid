package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R

private const val ARG_DATE = "date"

class SymptomListFragment : Fragment(), SymptomAdapter.onSymptomSelectedListener {
    lateinit var date: Date
    private lateinit var adapter: SymptomAdapter
    private var listener: SymptomAdapter.onSymptomSelectedListener? = null

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
        val root = inflater.inflate(R.layout.fragment_symptom_list, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.symptom_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { SymptomAdapter(it, this) }!!
        recyclerView.adapter = adapter
        adapter.addSnapshotListener()
        return root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = this
    }

    override fun onSymptomSelected(symptom: Symptom) {
        val fragment = SymptomFragment.newInstance(symptom)
        val ft: FragmentManager = parentFragmentManager
        ft.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("symptom")
            .commit()
        Log.d(Constants.TAG, "Adding symptom fragment")
    }
}