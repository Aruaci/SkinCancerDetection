package com.shellrider.skincancerdetectionur_crops.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.shellrider.minipainter.camera.CameraCapture
import com.shellrider.minipainter.camera.CameraPermission
import com.shellrider.skincancerdetectionur_crops.filesystem.writeBitMapToStorage
import kotlin.math.ceil


@Composable
fun FullView(navController: NavController){
    val context = LocalContext.current
    var layoutHeight by remember { mutableStateOf(0) }
    var layoutWidth by remember { mutableStateOf(0) }
    CameraPermission {
        CameraCapture(
            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                layoutHeight = layoutCoordinates.size.height
                layoutWidth = layoutCoordinates.size.width
            },
            previewScaleType = PreviewView.ScaleType.FILL_CENTER,
            onImageFile = { file ->

                //For some reason bitmap is flipped
                var bitmap = BitmapFactory.decodeFile(file.path)
                var cutHeight :Int = (bitmap.width) * (layoutWidth) / layoutHeight

                Log.d("FULL_VIEW", "Bitmap size - Height: ${bitmap.height} - Width: ${bitmap.width}")
                Log.d("FULL_VIEW", "LayoutHeight: $layoutHeight - LayoutWidth: $layoutWidth")
                Log.d("FULL_VIEW", "Need to cut bitmap to $cutHeight pixel first")

                bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    (bitmap.height - cutHeight) / 2,
                    bitmap.width,
                    cutHeight
                )

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
                Log.d("FULL_VIEW", "LayoutHeight: $layoutHeight - LayoutWidth: $layoutWidth")
                writeBitMapToStorage(context, bitmap)
                navController.navigate("image_viewer")
            }
        )
    }
}