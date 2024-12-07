package com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces

import android.graphics.Canvas
import com.yanchelenko.tableandgraphapp.ui.models.PointUI

interface Renderer {
    fun draw(canvas: Canvas, graphState: GraphStateController, cellSize: Float, points: List<PointUI>)
}
