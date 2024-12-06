package com.yanchelenko.tableandgraphapp.ui.table.recycler.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yanchelenko.tableandgraphapp.R
import com.yanchelenko.tableandgraphapp.ui.models.PointUI

class PointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    //todo binding
    private val xTextView: TextView = itemView.findViewById(R.id.textXItemTV)
    private val yTextView: TextView = itemView.findViewById(R.id.textYItemTV)

    fun bind(point: PointUI) {
        xTextView.text = point.x.toString()
        yTextView.text = point.y.toString()
    }
}
