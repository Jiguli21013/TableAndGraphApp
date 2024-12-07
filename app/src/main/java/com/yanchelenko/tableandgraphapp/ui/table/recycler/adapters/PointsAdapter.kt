package com.yanchelenko.tableandgraphapp.ui.table.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yanchelenko.tableandgraphapp.R
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.table.recycler.viewholders.HeaderPointViewHolder
import com.yanchelenko.tableandgraphapp.ui.table.recycler.viewholders.PointViewHolder
import kotlinx.collections.immutable.ImmutableList

class PointsAdapter(private val points: ImmutableList<PointUI>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_TYPE_HEADER else ITEM_TYPE_ITEM_POINT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_table_header, parent, false)
                HeaderPointViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_table_point, parent, false)
                PointViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderPointViewHolder -> {}
            is PointViewHolder -> {
                val point = points[position]
                holder.bind(point)
            }
        }
    }

    override fun getItemCount(): Int = points.size

    companion object {
        private const val ITEM_TYPE_HEADER = 0
        private const val ITEM_TYPE_ITEM_POINT = 1
    }
}
