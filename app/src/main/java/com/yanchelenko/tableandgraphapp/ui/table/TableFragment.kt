package com.yanchelenko.tableandgraphapp.ui.table

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yanchelenko.tableandgraphapp.R
import com.yanchelenko.tableandgraphapp.databinding.FragmentTableBinding
import com.yanchelenko.tableandgraphapp.ui.home.HomeViewModel
import com.yanchelenko.tableandgraphapp.ui.base.BaseFragment
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.table.graphview.saveViewToFile
import com.yanchelenko.tableandgraphapp.ui.table.recycler.adapters.PointsAdapter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class TableFragment : BaseFragment<FragmentTableBinding, TableViewModel>(R.layout.fragment_table) {

    override val viewModel: TableViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val generatedPoints = generateData().sortedBy { it.x }.toImmutableList()
        displayPoints(points = generatedPoints)
        binding.customViewTable.setData(generatedPoints)

        binding.saveFileBtn.setOnClickListener { customView ->
            //todo check android version and permissions
            saveViewToFile(view = customView)
        }
    }

    private fun displayPoints(points: ImmutableList<PointUI>) {
        //todo сделать красивей
        val adapter = PointsAdapter(points = points)
        binding.tablePointsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.tablePointsRV.adapter = adapter
    }
}

//todo delete
private fun generateData(): List<PointUI> {
    val min = -100f
    val max = 100f
    val points = mutableListOf<PointUI>()

    // Генерация 20 случайных точек
    for (i in 1..20) {
        val x = min + Math.random() * (max - min)  // Случайное значение x от -100 до 100
        val y = min + Math.random() * (max - min)  // Случайное значение y от -100 до 100
        points.add(PointUI(x.toFloat(), y.toFloat()))
    }

    val newPoints = points.sortedBy { it.x }
    return newPoints
}
