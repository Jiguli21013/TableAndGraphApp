package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.interfaces.IPointDrawer

class PointDrawer : IPointDrawer {
    override fun drawPoint(canvas: Canvas, x: Float, y: Float) {
        val pointPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 14f
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        canvas.drawCircle(x, y, 5f, pointPaint)
    }
}
