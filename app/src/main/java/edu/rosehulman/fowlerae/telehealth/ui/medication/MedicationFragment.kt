package edu.rosehulman.fowlerae.telehealth.ui.medication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MedicationFragment : Fragment() {
    private lateinit var adapter: MedicationAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_medication, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.medication_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        adapter = context?.let { MedicationAdapter(it) }!!
        recyclerView.adapter = adapter
        //  recyclerView.setHasFixedSize(true)
        adapter.addSnapshotListener()
        val currentDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        val output: String = formatter.format(currentDateTime)
        Log.d(Constants.TAG, "Time: $output")
        val date = root.findViewById<TextView>(R.id.date_text_view)
        date.text = output
        return root
    }
}