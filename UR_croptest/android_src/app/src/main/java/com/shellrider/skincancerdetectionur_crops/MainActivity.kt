package com.shellrider.skincancerdetectionur_crops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shellrider.skincancerdetectionur_crops.Screens.*
import com.shellrider.skincancerdetectionur_crops.ui.theme.SkinCancerDetectionUR_CropsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkinCancerDetectionUR_CropsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SkinCancerDetectionApp()
                }
            }
        }
    }
}

@Composable
fun SkinCancerDetectionApp() {
    val navController = rememberNavController()

    Box(modifier = Modifier) {
        NavHost(navController = navController, startDestination = "start") {
            composable("start") {
                StartScreen(navController = navController)
            }
            composable("full_view_with_crop") {
                FullViewWithCropBorder()
            }
            composable("square_view") {
                SquareView()
            }
            composable("original_view_with_crop") {
                OriginalViewWithCropBorder()
            }
            composable("full_view") {
                FullView()
            }
            composable("original_view") {
                OriginalView()
            }
        }

    }
}

