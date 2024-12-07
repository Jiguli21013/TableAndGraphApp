package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview

import android.graphics.Canvas
import android.graphics.Paint
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview.interfaces.IGridDrawer

class GridDrawer : IGridDrawer {
    override fun drawGridLine(canvas: Canvas, startX: Float, startY: Float, endX: Float, endY: Float, paint: Paint) {
        canvas.drawLine(startX, startY, endX, endY, paint)
    }
}
