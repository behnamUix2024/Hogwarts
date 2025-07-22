package com.behnamuix.hogwarts.View.screen

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.behnamuix.avacast.ui.theme.Body
import com.behnamuix.avacast.ui.theme.Title
import com.behnamuix.avacast.ui.theme.VazirFont
import com.behnamuix.avacast.ui.theme.VazirFontBold
import com.behnamuix.hogwarts.R
import androidx.core.graphics.createBitmap
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.behnamuix.hogwarts.MyketActivity
import com.behnamuix.hogwarts.downloadAndSaveImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HogwartsLetterContent() {

    //init Value
    var ctx = LocalContext.current
    var name by remember { mutableStateOf("") }
    var alpha by remember { mutableStateOf(0f) }
    var showLetter by remember { mutableStateOf(false) }
    var tf_status by remember { mutableStateOf(false) }

    var url_image_bg_second_letter =
        "https://uploadkon.ir/uploads/fba322_25file-000000006008620aa33cd8d51dcd98ce.png"
    var url by
    remember { mutableStateOf("https://uploadkon.ir/uploads/cf8322_25dda05b45-85a7-4263-b791-459d128e62b7-removalai-preview.png") }



    Column(
        modifier = Modifier
            .paint(
                painterResource(R.drawable.bg2),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BackButton()
            Spacer(Modifier.height(20.dp))
            Column(


                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                MyText3(
                    title = "نامه ی دعوت به مدرسه جادویی هاگوارتز با اسم  خودت!",
                    size = 18
                )
                val englishOnlyRegex = Regex("^[\\p{ASCII}]*$")
                //------------------------------------------------
                Row(){

                    TextField(
                        singleLine = true,
                        maxLines = 1,
                        textStyle = TextStyle(

                            fontFamily = VazirFont,
                            fontSize = 14.sp,

                            ),
                        modifier = Modifier
                            .weight(0.5f)

                            .padding(8.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        enabled = tf_status,
                        label = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = "نام خود را به انگلیسی وارد کنید",
                                fontFamily = VazirFont,
                                fontSize = 12.sp,
                                textAlign = TextAlign.End

                            )
                        },
                        value = name,
                        onValueChange = {
                            if (it.length <= 16) {
                                if (it.matches(englishOnlyRegex)) {
                                    name = it
                                }
                            }
                        }
                    )
                    Image(
                        painter = painterResource(R.drawable.img_owl),
                        contentDescription = "",
                        modifier = Modifier
                            .weight(0.2f)
                            .size(80.dp)
                    )
                }
                Text(

                    color = Color(0xFFF44336),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "« صبر داشته باشید تا نامه نمایش داده شود »",
                    fontSize = 10.sp,
                    fontFamily = VazirFontBold
                )


                Spacer(Modifier.height(2.dp))
                Box() {

                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = "",
                        modifier = Modifier
                            .size(900.dp)
                            .clickable(onClick = { showLetter = true })


                    )
                    Box(

                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .alpha(alpha)
                            .width(180.dp)
                            .height(520.dp)
                    ) {
                        Text(
                            text = "Dear " + name.capitalize() + " ,",
                            fontFamily = Title,
                            color = Color(0xFF4A4A4A),
                            fontSize = 10.sp,
                        )
                    }
                    Box(

                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .alpha(alpha)
                            .align(Alignment.TopEnd)
                            .width(200.dp)
                            .height(200.dp)
                    ) {
                        Column(
                            modifier = Modifier

                                .padding(end = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(

                                onClick = {
                                    ctx.startActivity(Intent(ctx, MyketActivity::class.java))

                                }) {
                                Icon(

                                    painter = painterResource(R.drawable.icon_download),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }

                if (showLetter) {
                    alpha = 1f
                    tf_status = true
                    url = url_image_bg_second_letter
                }


            }


        }

    }

    @Composable
    fun BackButton() {
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .padding(start = 22.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .size(36.dp),
                onClick = { navigator.pop() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = "بازگشت",
                    tint = Color.White
                )

            }

        }

    }
}

@Composable
fun MyText3(title: String, size: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        text = title,
        fontFamily = VazirFontBold,
        style = TextStyle(textDirection = TextDirection.Rtl),
        fontSize = size.sp,
        color = androidx.compose.ui.graphics.Color.White,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ChangeImage(url: String) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = "",

        modifier = Modifier.size(200.dp)
    )

}



