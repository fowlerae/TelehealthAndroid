package edu.rosehulman.fowlerae.telehealth.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import edu.rosehulman.fowlerae.telehealth.ui.medication.MedicationAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SettingsFragment : Fragment() {

    private lateinit var adapter: SettingsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.setting_recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context,2)
        adapter = context?.let { SettingsAdapter(it) }!!
        recyclerView.adapter = adapter
     //   recyclerView.setHasFixedSize(true)
        adapter.addSnapshotListener()
        return root
    }
}