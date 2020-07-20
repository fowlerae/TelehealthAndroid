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
import com.google.firebase.firestore.*
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import edu.rosehulman.fowlerae.telehealth.ui.symptom.Date
import edu.rosehulman.fowlerae.telehealth.ui.symptom.SymptomListFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeFragment : Fragment() {
    private lateinit var adapter: BadgeAdapter
    private lateinit var listenerRegistration: ListenerRegistration
    private val dates = ArrayList<Date>()
    private val datesRef = FirebaseFirestore
        .getInstance()
        .collection("users")
        .document("UGSe2Si5KAsB0sQb9Gf7")
        .collection("dates")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.badge_recycler_view) as RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = context?.let { BadgeAdapter(it) }!!
        recyclerView.adapter = adapter
      //  recyclerView.setHasFixedSize(true)
        adapter.addSnapshotListener()
        val calendarView = root.findViewById<CalendarView>(R.id.calendar_view)
        var date = calendarView.date
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var date = LocalDate.of(year, month + 1, dayOfMonth)
//            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, YYYYY")
//            val output:String = formatter.format(date)
            var formattedDate =
                Date(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
            //      Date = dateRef.document("${formattedDate.name}")
            var found: Boolean = false
            for (x in dates) {
                if (x.id == date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))) {
                    found = true
                    x.name = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
                    Log.d(Constants.TAG, "Found Date: ${x.name}, ${x.qualityOfSleep},${x.id}")
                    onDateSelected(x)
                }
            }
            if (!found) {
                Log.d(Constants.TAG, "Not found Date: $formattedDate")
                datesRef.document("${formattedDate.name}").set(formattedDate)
                onDateSelected(formattedDate)
            }

        }
        addSnapshotListener()
        return root
    }

    private fun onDateSelected(date: Date) {
        val fragment: SymptomListFragment = SymptomListFragment.newInstance(date)
        val fragmentManager: FragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("date")
            .commit()

    }


    fun addSnapshotListener() {
        listenerRegistration = datesRef
            .orderBy(Date.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "listen error", e)
                } else {
                    processSnapshotChanges(querySnapshot!!)
                }
            }
    }

    private fun processSnapshotChanges(querySnapshot: QuerySnapshot) {
        // Snapshots has documents and documentChanges which are flagged by type,
        // so we can handle C,U,D differently.
        for (documentChange in querySnapshot.documentChanges) {
            val date = Date.fromSnapshot(documentChange.document)
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    Log.d(Constants.TAG, "Adding $date")
                    dates.add(0, date)
                }
                DocumentChange.Type.REMOVED -> {
                    Log.d(Constants.TAG, "Removing $date")
                    val index = dates.indexOfFirst { it.id == date.id }
                    dates.removeAt(index)
                }
                DocumentChange.Type.MODIFIED -> {
                    Log.d(Constants.TAG, "Modifying $date")
                    val index = dates.indexOfFirst { it.id == date.id }
                    dates[index] = date
                }
            }
        }
    }

}