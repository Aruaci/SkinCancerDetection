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
fun FullViewWithCropBorder(navController: NavController){
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
            previewScaleType = PreviewView.ScaleType.FILL_CENTER,
            cameraOverlay = { CropBorderOverlay() },
            onImageFile = { file ->

                //For some reason bitmap is flipped
                var bitmap = BitmapFactory.decodeFile(file.path)
                var cutHeight :Int = (bitmap.width) * (layoutWidth) / layoutHeight

                Log.d("FULL_WITH_CROP", "Bitmap size - Height: ${bitmap.height} - Width: ${bitmap.width}")
                Log.d("FULL_WITH_CROP", "LayoutHeight: $layoutHeight - LayoutWidth: $layoutWidth")
                Log.d("FULL_WITH_CROP", "Need to cut bitmap to $cutHeight pixel first")

                bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    (bitmap.height - cutHeight) / 2,
                    bitmap.width,
                    cutHeight
                )

                val cropAwaySize = ceil(localDensity*320 * (bitmap.width) / layoutHeight).toInt()

                Log.d("FULL_WITH_CROP", "Crop away: $cropAwaySize")

                bitmap = Bitmap.createBitmap(
                    bitmap,
                    (bitmap.width - cropAwaySize) /2 ,
                    (bitmap.height - cropAwaySize) / 2,
                    cropAwaySize,
                    cropAwaySize
                )

                Log.d("FULL_WITH_CROP", "LayoutHeight: $layoutHeight - LayoutWidth: $layoutWidth")
                writeBitMapToStorage(context, bitmap)
                navController.navigate("image_viewer")
            }
        )
    }
}