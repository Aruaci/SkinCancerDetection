package com.shellrider.skincancerdetectionur_crops.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission
import com.shellrider.skincancerdetectionur_crops.filesystem.writeBitMapToStorage
import kotlin.math.ceil

@Composable
fun SquareView(navController: NavController) {
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
                writeBitMapToStorage(context, bitmap)
                navController.navigate("image_viewer")
            }
        )
    }
}