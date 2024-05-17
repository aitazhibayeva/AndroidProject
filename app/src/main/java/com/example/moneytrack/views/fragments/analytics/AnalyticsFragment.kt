package com.example.moneytrack.views.fragments.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moneytrack.R
import com.example.moneytrack.utils.Database.AppDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsFragment : Fragment() {

    private lateinit var pieChart: PieChart

    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analytics, container, false)

        pieChart = view.findViewById(R.id.pie_chart)

        viewModel.pieEntries.observe(viewLifecycleOwner) { entries ->
            val pieDataSet = PieDataSet(entries, "List")
            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            pieDataSet.valueTextColor = android.graphics.Color.BLACK
            pieDataSet.valueTextSize = 15f

            val pieData = PieData(pieDataSet)
            pieChart.data = pieData
            pieChart.description.text = "Pie Chart"
            pieChart.centerText = "List"
            pieChart.animateY(2000)
        }

        return view
    }

    companion object {
        fun newInstance() = AnalyticsFragment()
    }
}
