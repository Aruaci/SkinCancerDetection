package com.shellrider.skincancerdetectionur_crops.Screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission
import com.shellrider.skincancerdetectionur_crops.filesystem.writeBitMapToStorage
import kotlin.math.ceil

@Composable
fun OriginalView(navController: NavController){
    val context = LocalContext.current

    CameraPermission {
        CameraCapture(
            previewScaleType = PreviewView.ScaleType.FIT_CENTER,
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
                        0,
                        ceil(((bitmap.height-bitmap.width) / 2.0)).toInt(),
                        bitmap.width,
                        bitmap.width)
                }
                writeBitMapToStorage(context, bitmap)
                navController.navigate("image_viewer")
            }
        )
    }
}