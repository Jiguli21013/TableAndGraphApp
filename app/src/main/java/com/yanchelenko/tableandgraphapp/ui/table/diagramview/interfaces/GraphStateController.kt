package com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces

interface GraphStateController {
    var offsetX: Float
    var offsetY: Float
    var scaleFactor: Float

    var graphWidth: Float
    var graphHeight: Float

    val cellSize: Float

    val viewWidth: Float
    val viewHeight: Float

    fun resetGraph()
    fun constrainOffsets()
    fun updateScale(focusX: Float, focusY: Float, scaleDelta: Float)
    fun invalidateView()
}
