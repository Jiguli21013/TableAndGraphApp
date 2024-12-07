package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview

import android.graphics.Canvas
import android.graphics.Path
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.GraphStateController
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.Renderer

class GraphRenderer : Renderer {

    private val pointDrawer = PointDrawer()
    private val textDrawer = TextDrawer()
    private val lineDrawer = LineDrawer()

    override fun draw(canvas: Canvas, graphState: GraphStateController, cellSize: Float, points: List<PointUI>) {
        val centerX = graphState.viewWidth / 2f
        val centerY = graphState.viewHeight / 2f
        val path = Path()

        for ((index, point) in points.withIndex()) {
            val x = centerX + (point.x * cellSize) + graphState.offsetX
            val y = centerY - (point.y * cellSize) + graphState.offsetY

            // Рисуем точку
            pointDrawer.drawPoint(canvas, x, y)

            // Рисуем текст с координатами
            val text = "(${point.x.toInt()}, ${point.y.toInt()})"
            textDrawer.drawText(canvas, x, y, text)

            // Соединяем точки
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // Рисуем линии
        lineDrawer.drawLine(canvas, path)
    }
}