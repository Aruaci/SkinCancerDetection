package com.shellrider.skincancerdetectionur_crops.filesystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

const val LOG_TAG = "SCD_FS"

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

/* Checks if external storage is available for read and write */
fun isExternalStorageWritable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state
}

/* Checks if external storage is available to at least read */
fun isExternalStorageReadable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
}

fun getAppSpecificAlbumStorageDir(context: Context, albumName: String): File? {
    // Get the pictures directory that's inside the app-specific directory on
    // external storage.
    val file = File(context.getExternalFilesDir(
        Environment.DIRECTORY_PICTURES), albumName)
    if (!file?.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created, check if directory already exists. If directory already exists this is not a problem.")
    }
    return file
}

fun writeImageToStorage(context: Context, file: File, filename: String){
    if(isExternalStorageWritable()) {
        val dir = getAppSpecificAlbumStorageDir(context, "moles")
        val writeFile = File(dir, filename)
        try {
            val fileOutputStream = FileOutputStream(writeFile)
            fileOutputStream.write(file.readBytes())
            fileOutputStream.close()
            Log.d(LOG_TAG, "Wrote ${writeFile.path}")
        } catch (ex: Exception) {
            Log.e(LOG_TAG, "Error while writing file to external storage.", ex)
        }
    }
}

fun writeBitMapToStorage(context: Context, bitmap: Bitmap) : File? {
    var file = File(context.cacheDir, "cachedImage")
    var bitmap = bitmap.rotate(90f)
    try {
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    } catch (ex: Exception) {
        Log.e(LOG_TAG, "Error while writing jpg to cache.", ex)
    }
    return file
}