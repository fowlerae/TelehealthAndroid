package edu.rosehulman.fowlerae.telehealth.ui.symptom

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R

private const val ARG_SYMPTOM = "symptom"
private const val ARG_DATE = "date"

class AddSymptomFragment() : Fragment() {
    private lateinit var adapter: AddSymptomAdapter
    private lateinit var date: Date

    companion object {
        @JvmStatic
        fun newInstance(d: Date) =
            AddSymptomFragment().apply {
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
        val root = inflater.inflate(R.layout.fragment_add_symptom, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.add_symptom_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { AddSymptomAdapter(it, date) }!!
        recyclerView.adapter = adapter
        adapter.addSnapshotListener()
        val addSymptomName = root.findViewById<EditText>(R.id.add_symptom_name)
        addSymptomName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (event == null || !event.isShiftPressed()) {
                    // the user is done typing.
                    addSymptom(v.text as String)
                    return@setOnEditorActionListener true; // consume.
                }
            }
            return@setOnEditorActionListener false; // pass on to other listeners.
        }
        return root
    }

    private fun addSymptom(name: String) {
        val symptom = Symptom(name)
        val fragment = NewlyAddedSymptomFragment.newInstance(symptom, date)
        val ft: FragmentManager = parentFragmentManager
        ft.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("symptom")
            .commit()
        Log.d(Constants.TAG, "Adding symptom fragment")
    }

}