package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview.interfaces

import android.graphics.Canvas
import android.graphics.Paint

interface IAxisDrawer {
    fun drawAxis(canvas: Canvas, startX: Float, startY: Float, endX: Float, endY: Float, paint: Paint)
}
