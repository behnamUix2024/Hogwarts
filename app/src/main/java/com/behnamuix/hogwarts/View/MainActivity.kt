package com.behnamuix.hogwarts.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import com.behnamuix.hogwarts.View.screen.MainSc
import com.behnamuix.hogwarts.ui.theme.MvpComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = Color.Black.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
        }

        setContent {
            MvpComposeTheme {


                Navigator(MainSc)


            }
        }
    }


}

