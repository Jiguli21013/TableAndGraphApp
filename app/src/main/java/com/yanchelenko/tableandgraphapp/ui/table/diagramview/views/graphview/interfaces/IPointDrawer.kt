package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.interfaces

import android.graphics.Canvas

interface IPointDrawer {
    fun drawPoint(canvas: Canvas, x: Float, y: Float)
}
