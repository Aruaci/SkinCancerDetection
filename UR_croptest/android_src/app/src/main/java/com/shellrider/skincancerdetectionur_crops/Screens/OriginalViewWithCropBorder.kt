package com.shellrider.skincancerdetectionur_crops.Screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavController
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission
import com.shellrider.skincancerdetectionur_crops.Screens.overlays.CropBorderOverlay
import com.shellrider.skincancerdetectionur_crops.filesystem.writeBitMapToStorage
import kotlin.math.ceil

@Composable
fun OriginalViewWithCropBorder(navController: NavController) {
    val context = LocalContext.current
    var layoutHeight by remember { mutableStateOf(0) }
    var layoutWidth by remember { mutableStateOf(0) }
    var localDensity = LocalDensity.current.density

    CameraPermission {
        CameraCapture(
            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                layoutHeight = layoutCoordinates.size.height
                layoutWidth = layoutCoordinates.size.width
            },
            previewScaleType = PreviewView.ScaleType.FIT_CENTER,
            cameraOverlay = { CropBorderOverlay() },
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