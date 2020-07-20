package edu.rosehulman.fowlerae.telehealth.ui.overview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import edu.rosehulman.fowlerae.telehealth.R


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_overview, container, false)
        makePieChart(root)
        makeBarChart(root)
        return root
    }

    private fun makePieChart(root: View) {
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
    }

    private fun makeBarChart(root: View) {
        val barChart = root.findViewById(R.id.bar_chart) as BarChart
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 4.toFloat()))
        entries.add(BarEntry(1f, 1.toFloat()))
        entries.add(BarEntry(2f, 2.toFloat()))
        entries.add(BarEntry(3f, 3.toFloat()))
        entries.add(BarEntry(4f, 4.toFloat()))
        entries.add(BarEntry(5f, 5.toFloat()))

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


}