package com.shellrider.skincancerdetectionur_crops.Screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission
import com.shellrider.skincancerdetectionur_crops.filesystem.writeBitMapToStorage
import kotlin.math.ceil

@Composable
fun SquareView() {
    val context = LocalContext.current


    @Composable
    fun overlay(){
        Canvas(modifier = Modifier.fillMaxSize()){
            drawRect(
                color = Color.Black,
                topLeft = Offset(0f,0f),
                size = Size(size.width,size.height/2-size.width/2)
            )
            drawRect(
                color = Color.Black,
                topLeft = Offset(0f,size.height-size.width/2),
                size = Size(size.width,size.height/2-size.width/2)
            )
        }
    }

    CameraPermission {
        CameraCapture(
            cameraOverlay = { overlay() },
            onImageFile = { file ->
                var bitmap = BitmapFactory.decodeFile(file.path)
                Log.d("SQUARE_VIEW", "Bitmap width: ${bitmap.width} - Bitmap height: ${bitmap.height}")
                if(bitmap.width >= bitmap.height) {
                    bitmap = Bitmap.createBitmap(bitmap,
                        ceil(((bitmap.width-bitmap.height) / 2.0)).toInt(),
                        0,
                        bitmap.height,
                        bitmap.height)
                } else {
                    bitmap = Bitmap.createBitmap(bitmap,
                        ceil(bitmap.height-bitmap.width/2.0).toInt(),
                        0,
                        bitmap.width,
                        bitmap.width)
                }
                Log.d("SQUARE_VIEW", "Bitmap width: ${bitmap.width} - Bitmap height: ${bitmap.height}")
                var cachedFile = writeBitMapToStorage(context, bitmap)
                Log.d("SQUARE_VIEW", "Cached Filepath: ${cachedFile?.path}")
            }
        )
    }
}