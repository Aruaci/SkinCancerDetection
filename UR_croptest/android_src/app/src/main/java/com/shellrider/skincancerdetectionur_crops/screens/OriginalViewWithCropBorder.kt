package com.shellrider.skincancerdetectionur_crops.screens

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
import com.shellrider.skincancerdetectionur_crops.screens.overlays.CropBorderOverlay
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
            onImageFile  = { file ->
                var bitmap = BitmapFactory.decodeFile(file.path)
                val cropAwaySize = ceil(localDensity*320 * (bitmap.height) / layoutWidth).toInt()

                Log.d("ORIGINAL_WITH_CROP", "Crop away: $cropAwaySize")

                bitmap = Bitmap.createBitmap(
                    bitmap,
                    (bitmap.width - cropAwaySize) /2 ,
                    (bitmap.height - cropAwaySize) / 2,
                    cropAwaySize,
                    cropAwaySize
                )

                Log.d("ORIGINAL_WITH_CROP", "LayoutHeight: $layoutHeight - LayoutWidth: $layoutWidth")
                writeBitMapToStorage(context, bitmap)
                navController.navigate("image_viewer")
            }
        )
    }
}