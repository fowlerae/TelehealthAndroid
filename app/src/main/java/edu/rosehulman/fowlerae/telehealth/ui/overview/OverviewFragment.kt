package edu.rosehulman.fowlerae.telehealth.ui.overview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import edu.rosehulman.fowlerae.telehealth.R


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_overview, container, false)

        val pieChart: PieChart = root.findViewById(R.id.pie_chart)
        val yValues: ArrayList<PieEntry> = ArrayList()
        yValues.add(PieEntry(34f, "Ilala"))
        yValues.add(PieEntry(56f, "Temeke"))
        yValues.add(PieEntry(66f, "Kinondoni"))
        yValues.add(PieEntry(45f, "Kigamboni"))
        val dataSet = PieDataSet(yValues, "Number Of Employees")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val pieData = PieData(dataSet)
        pieData.setValueTextSize(10f)
        pieData.setValueTextColor(Color.YELLOW)
        pieChart.data = pieData
        return root
    }
}