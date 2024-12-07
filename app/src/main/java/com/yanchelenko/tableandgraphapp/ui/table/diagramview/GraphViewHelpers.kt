package com.yanchelenko.tableandgraphapp.ui.table.diagramview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

//todo где хранить эти функции?
fun saveViewToFile(view: View): Boolean {
    val bitmap = getBitmapFromView(view = view)
                                                                                                            //todo to constant?
    val filePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "graph_image.png")
    return try {
        val fos = FileOutputStream(filePath)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

private fun getBitmapFromView(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    view.draw(canvas)
    return bitmap
}
