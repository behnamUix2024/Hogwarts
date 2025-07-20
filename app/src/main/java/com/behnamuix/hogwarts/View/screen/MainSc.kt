package com.behnamuix.hogwarts.View.screen

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.behnamuix.avacast.ui.theme.HarryFont
import com.behnamuix.avacast.ui.theme.VazirFont
import com.behnamuix.hogwarts.Harrypoter.audioManager.SongPlayer
import com.behnamuix.hogwarts.R

@Composable
fun MainContent() {
    var ctx = LocalContext.current
    var audio = SongPlayer(ctx)
    audio.play()
    Toast.makeText(ctx, "ساخته شده با ❤ توسط Arcadroid", Toast.LENGTH_LONG).show()
    Column(
        modifier = Modifier
            .paint(
                painterResource(R.drawable.bg1),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(start = 22.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .size(36.dp),
                onClick = { if (audio.isPlaying()) audio.pause() else audio.play() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.icon_song),
                    contentDescription = "بازگشت",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Hogwarts\n\n\nSchool v1",
            textAlign = TextAlign.Center,
            fontFamily = HarryFont,
            color = Color.White,
            fontSize = 66.sp
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier

                    .padding(top = 2.dp)
                    .fillMaxSize()
        ) {

            Column {
                Column(
                    modifier = Modifier
                        .padding(12.dp),
                ) {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                modifier = Modifier
                                    .padding(end = 12.dp),
                                text = "آلبوس دامبلدور:",
                                color = Color.White,
                                fontFamily = VazirFont,
                                style = TextStyle(textDirection = TextDirection.Rtl),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box() {
                                Image(
                                    modifier = Modifier
                                        .size(66.dp)
                                        .clip(RoundedCornerShape(18.dp)),
                                    //painter = painterResource(R.drawable.img_profile),
                                    painter = rememberAsyncImagePainter("https://static1.cbrimages.com/wordpress/wp-content/uploads/2022/04/Dumbledore-2.jpg"),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )

                            }

                        }
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 70.dp),
                        text = " به مدرسهٔ جادوگری هاگوارتز خوش آمدید، جایی که اسرار کهن با جادوی نو در هم میآمیزند!" +

                                "آیا آماده اید تا سرنوشت جادویی\u200Cتان را کشف کنید؟\n" +
                                "کلاه گروه\u200Cبندی افسانه\u200Cای هاگوارتز، با صدایی آهسته و پر از حکمت، تنها با چند پرسش ساده می\u200Cتواند خانهٔ واقعی شما را در این قلعهٔ پررمزوراز مشخص کند!",
                        color = Color.White,
                        fontFamily = VazirFont,
                        style = TextStyle(textDirection = TextDirection.Rtl),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                }


            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()

            ) {
                AnimationHat()

            }


        }

    }


}

@Composable
fun AnimationHat() {
    var navigator = LocalNavigator.currentOrThrow
    val animatable = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatable.animateTo(
            targetValue = 80f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Image(
        modifier = Modifier
            .clickable(onClick = { navigator.push(HogwartsTestSc) })
            .graphicsLayer(translationX = 0f, translationY = animatable.value)
            .size(200.dp),
        painter = painterResource(R.drawable.hat),
        contentDescription = "",

        )
}
