package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.interfaces.ILineDrawer

class LineDrawer : ILineDrawer {
    override fun drawLine(canvas: Canvas, path: Path) {
        val linePaint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 4f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        canvas.drawPath(path, linePaint)
    }
}
