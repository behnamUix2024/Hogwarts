package com.behnamuix.hogwarts.View

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import com.behnamuix.hogwarts.Harrypoter.audioManager.SongPlayer
import com.behnamuix.hogwarts.View.screen.GroupDetailContent
import com.behnamuix.hogwarts.View.screen.GroupDetailSc
import com.behnamuix.hogwarts.View.screen.HogwartsLetterSc
import com.behnamuix.hogwarts.View.screen.HogwartsTestSc
import com.behnamuix.hogwarts.View.screen.MainSc
import com.behnamuix.hogwarts.ui.theme.MvpComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.OutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = Color.Black.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
        }
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )
        setContent {
            MvpComposeTheme {


                Navigator(HogwartsTestSc)


            }
        }
    }


}


