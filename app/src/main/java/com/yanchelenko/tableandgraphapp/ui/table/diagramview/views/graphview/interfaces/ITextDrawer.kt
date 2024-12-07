package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.interfaces

import android.graphics.Canvas

interface ITextDrawer {
    fun drawText(canvas: Canvas, x: Float, y: Float, text: String)
}
