package edu.rosehulman.fowlerae.telehealth.ui.overview

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.*
import edu.rosehulman.fowlerae.telehealth.Constants
import edu.rosehulman.fowlerae.telehealth.R
import edu.rosehulman.fowlerae.telehealth.ui.symptom.Date


class OverviewFragment : Fragment() {
    private val hashMap = HashMap<String, Int>()
    private val dates = ArrayList<Date>()
    private val datesRef = FirebaseFirestore
        .getInstance()
        .collection("users")
        .document("UGSe2Si5KAsB0sQb9Gf7")
        .collection("dates")

    private lateinit var listenerRegistration: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_overview, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        addSnapshotListenerDates()
        makePieChart(root)
        makeBarChart(root)
        return root
    }

    private fun makePieChart(root: View) {
        val pieChart: PieChart = root.findViewById(R.id.pie_chart)
        val yValues: ArrayList<PieEntry> = ArrayList()
        yValues.add(PieEntry(67f, "Migraine"))
        yValues.add(PieEntry(10f, "Knee Pain"))
        yValues.add(PieEntry(5f, "Light Sensitivity"))
        yValues.add(PieEntry(19f, "Back Pain"))
        val dataSet = PieDataSet(yValues, "Symptom")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val pieData = PieData(dataSet)
        pieData.setValueTextSize(10f)
        pieData.setValueTextColor(Color.YELLOW)
        pieChart.data = pieData
    }

    private fun makeBarChart(root: View) {
        val barChart = root.findViewById(R.id.bar_chart) as BarChart
        val entries = ArrayList<BarEntry>()

        entries.add(BarEntry(0f, 6.toFloat()))
        entries.add(BarEntry(1f, 8.toFloat()))
        entries.add(BarEntry(2f, 5.toFloat()))
        entries.add(BarEntry(3f, 7.toFloat()))
        entries.add(BarEntry(4f, 10.toFloat()))
        entries.add(BarEntry(5f, 9.toFloat()))

        val barDataSet = BarDataSet(entries, "Pain Rating")
        val data = BarData(barDataSet)

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels);
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.YELLOW)
        barDataSet.color = resources.getColor(R.color.colorAccent)
        barChart.animateY(5000)
        barChart.data = data

    }


    fun addSnapshotListenerDates() {
        listenerRegistration = datesRef
            .orderBy(Date.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "listen error", e)
                } else {
                    processSnapshotChangesDates(querySnapshot!!)
                }
            }
    }

    private fun processSnapshotChangesDates(querySnapshot: QuerySnapshot) {
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