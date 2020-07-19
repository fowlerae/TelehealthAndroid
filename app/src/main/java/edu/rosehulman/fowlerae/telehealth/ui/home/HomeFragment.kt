package edu.rosehulman.fowlerae.telehealth.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import edu.rosehulman.fowlerae.telehealth.ui.symptom.Date
import edu.rosehulman.fowlerae.telehealth.ui.symptom.SymptomListFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeFragment : Fragment() {
    private lateinit var adapter: BadgeAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.badge_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        adapter = context?.let { BadgeAdapter(it) }!!
        recyclerView.adapter = adapter
      //  recyclerView.setHasFixedSize(true)
        adapter.addSnapshotListener()
        val calendarView = root.findViewById<CalendarView>(R.id.calendar_view)
        var date = calendarView.date
        calendarView.setOnDateChangeListener { view, year, month ,dayOfMonth ->
            var date = LocalDate.of(year, month + 1, dayOfMonth)
//            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, YYYYY")
//            val output:String = formatter.format(date)
            var formattedDate =
                Date(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
            Log.d(Constants.TAG, "Date: $formattedDate")
            onDateSelected(formattedDate)
        }
        return root
    }

    private fun onDateSelected(date: Date) {
        val fragment :SymptomListFragment =  SymptomListFragment.newInstance(date)
        val fragmentManager : FragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("date")
            .commit()

    }
}