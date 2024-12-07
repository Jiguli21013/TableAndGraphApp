package com.yanchelenko.tableandgraphapp.ui.table

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yanchelenko.tableandgraphapp.R
import com.yanchelenko.tableandgraphapp.databinding.FragmentTableBinding
import com.yanchelenko.tableandgraphapp.ui.base.BaseFragment
import com.yanchelenko.tableandgraphapp.ui.models.ListOfPointsUI
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.saveViewToFile
import com.yanchelenko.tableandgraphapp.ui.table.recycler.adapters.PointsAdapter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class TableFragment : BaseFragment<FragmentTableBinding, TableViewModel>(R.layout.fragment_table) {

    override val viewModel: TableViewModel by viewModels()
    private val args by navArgs<TableFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayPoints(points = args.listOfPointsArgs ?: return)
        binding.customViewTable.setData(newData = args.listOfPointsArgs ?: return)

        binding.saveFileBtn.setOnClickListener { customView ->
            //todo check android version and permissions
            saveViewToFile(view = customView)
        }
    }

    private fun displayPoints(points: ListOfPointsUI) {
        //todo сделать красивей
        val adapter = PointsAdapter(points = points.points.toImmutableList())
        binding.tablePointsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.tablePointsRV.adapter = adapter
    }
}

