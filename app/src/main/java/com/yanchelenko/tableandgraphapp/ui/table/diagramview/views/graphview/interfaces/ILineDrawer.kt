package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.interfaces

import android.graphics.Canvas
import android.graphics.Path

interface ILineDrawer {
    fun drawLine(canvas: Canvas, path: Path)
}
