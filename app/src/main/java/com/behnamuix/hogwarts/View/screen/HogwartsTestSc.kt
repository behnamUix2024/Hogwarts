package com.behnamuix.hogwarts.View.screen

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.behnamuix.avacast.ui.theme.VazirFont
import com.behnamuix.avacast.ui.theme.VazirFontBold
import com.behnamuix.hogwarts.Harrypoter.groups.Gryffindor
import com.behnamuix.hogwarts.Harrypoter.groups.Hufflepuff
import com.behnamuix.hogwarts.Harrypoter.groups.Ravenclaw
import com.behnamuix.hogwarts.Harrypoter.groups.Slytherin
import com.behnamuix.hogwarts.R

data class HouseProgress(
    val gryffindor: Float,
    val slytherin: Float,
    val ravenclaw: Float,
    val hufflepuff: Float
)

data class QuizQuestion(
    val id: Int,
    val text: String,
    val answers: List<String>
)

@Composable
fun HogwartsTestContent() {
    val ctx = LocalContext.current

    var progressState by remember {
        mutableStateOf(
            HouseProgress(
                gryffindor = 0f,
                slytherin = 0f,
                ravenclaw = 0f,
                hufflepuff = 0f
            )
        )
    }

    var showCompletionToast by remember { mutableStateOf(false) }
    var completedHouse by remember { mutableStateOf("") }

    fun checkHouseCompletion(progress: Float, houseName: String) {
        if (progress >= 0.999f && !showCompletionToast) {
            completedHouse = houseName
            showCompletionToast = true
        }
    }

    if (showCompletionToast) {
        Toast.makeText(
            ctx,
            "تبریک! شما از خون $completedHouse هستید! ",
            Toast.LENGTH_LONG
        ).show()
        showCompletionToast = false
    }

    val questions = listOf(
        QuizQuestion(
            1,
            "کدام مورد برای شما مهم تر است؟",
            listOf(
                "شجاعت و ماجراجویی",
                "قدرت و بلندپروازی",
                "دانش و خرد",
                "وفاداری و عدالت"
            )
        ),
        QuizQuestion(
            2,
            "اگر در یک جنگل گم شوید، چه می کنید؟",
            listOf(
                "به دنبال نشانه‌ها می‌گردم",
                "از جادو استفاده می‌کنم",
                "از منطق استفاده می کنم",
                "به کمک امیدوار می‌مانم"
            )
        ),
        QuizQuestion(
            3,
            "کدام حیوان را ترجیح می دهید؟",
            listOf(
                "شیر",
                "مار",
                "عقاب",
                "گورکن"
            )
        ),
        QuizQuestion(
            4,
            "دوستانتان شما را چگونه توصیف می کنند؟",
            listOf(
                "بی پروا و رهبر",
                "بلندپرواز و مصمم",
                "عاقل و کنجکاو",
                "صادق و دلسوز"
            )
        ),
        QuizQuestion(
            5,
            "کدام شیء جادویی را انتخاب می کنید؟",
            listOf(
                "⚔ شمشیر گودریک",
                "💍 گردنبند سالازار",
                "👑 تاج روونا",
                "☕ فنجان هلفا"
            )
        ),
        QuizQuestion(
            6,
            "در موقعیت خطر، واکنش شما چیست؟",
            listOf(
                "بدون ترس وارد عمل میشوم",
                "از تاکتیک استفاده می کنم",
                "اول تحلیل بعد اقدام",
                "کمک به دیگران"
            )
        ),
        QuizQuestion(
            7,
            "کدام رنگ را بیشتر دوست دارید؟",
            listOf(
                "قرمز و طلایی",
                "سبز و نقره ای",
                "آبی و برنزی",
                "زرد و سیاه"
            )
        )

    )
    val navigator = LocalNavigator.currentOrThrow

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .paint(
                painterResource(R.drawable.bg2),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
    ) {
        BackButton(navigator)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            MyText(
                title = "با جواب دادن به پرسش های زیر کلاه\nگروهتون رو مشخص میکنه!",
                size = 14,
                isTitle = false
            )
        }

        HousesProgressRow(progressState)

        QuizQuestionsList(
            questions = questions,
            onAnswerSelected = { houseIndex ->
                progressState = when (houseIndex) {
                    0 -> {
                        val newProgress = (progressState.gryffindor + 0.1429f).coerceAtMost(1f)
                        checkHouseCompletion(newProgress, Gryffindor().getHouseName())
                        progressState.copy(gryffindor = newProgress)
                    }
                    1 -> {
                        val newProgress = (progressState.slytherin + 0.1429f).coerceAtMost(1f)
                        checkHouseCompletion(newProgress, Slytherin().getHouseName())
                        progressState.copy(slytherin = newProgress)
                    }
                    2 -> {
                        val newProgress = (progressState.ravenclaw + 0.1429f).coerceAtMost(1f)
                        checkHouseCompletion(newProgress, Ravenclaw().getHouseName())
                        progressState.copy(ravenclaw = newProgress)
                    }
                    3 -> {
                        val newProgress = (progressState.hufflepuff + 0.1429f).coerceAtMost(1f)
                        checkHouseCompletion(newProgress, Hufflepuff().getHouseName())
                        progressState.copy(hufflepuff = newProgress)
                    }
                    else -> progressState
                }
            }
        )
    }
}

@Composable
fun BackButton(navigator: Any) {
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

@Composable
fun HousesProgressRow(progressState: HouseProgress) {
    Row(modifier = Modifier.padding(top = 2.dp)) {
        HouseProgressItem(
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/1-16.jpg",
            progress = progressState.gryffindor,
            color = Color(0xFF840000),
            houseName = "گریفیندور",
            click = {}
        )
        HouseProgressItem(
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/2-18.jpg",
            progress = progressState.slytherin,
            color = Color(0xFF608100),
            houseName = "اسلیترین",
            click = {}
        )
        HouseProgressItem(
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/3-11.jpg",
            progress = progressState.ravenclaw,
            color = Color(0xFF016084),
            houseName = "ریونکلاو",
            click = {}
        )
        HouseProgressItem(
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/4-2.jpg",
            progress = progressState.hufflepuff,
            color = Color(0xFFCE9D09),
            houseName = "هافلپاف",
            click = {}
        )
    }
}

@Composable
fun HouseProgressItem(
    imageUrl: String,
    progress: Float,
    color: Color,
    houseName: String,
    click: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0x2D000000)),
        modifier = Modifier
            .clickable(onClick = click)
            .width(100.dp)
            .padding(horizontal = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp, bottomEnd = 0.dp, bottomStart = 0.dp)),
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = houseName,
                contentScale = ContentScale.Crop
            )
            LinearProgressIndicator(
                progress = { progress },
                color = color,
                modifier = Modifier
                    .width(100.dp)
                    .padding(12.dp)
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                color = color,
                fontFamily = VazirFontBold
            )
        }
    }
}

@Composable
fun QuizQuestionsList(
    questions: List<QuizQuestion>,
    onAnswerSelected: (Int) -> Unit
) {
    LazyColumn {
        itemsIndexed(questions) { index, question ->
            QuizCard(
                questionNumber = index + 1,
                question = question,
                onAnswerSelected = onAnswerSelected
            )
        }
    }
}

@Composable
fun QuizCard(
    questionNumber: Int,
    question: QuizQuestion,
    onAnswerSelected: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0x75000000)),
        modifier = Modifier
            .padding(vertical = 40.dp)
            .fillMaxWidth(0.7f)
            .height(450.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            MyText(
                title = questionNumber.toString(),
                size = 20,
                isTitle = true
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = question.text,
                fontFamily = VazirFontBold,
                style = TextStyle(textDirection = TextDirection.Rtl),
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                question.answers.forEachIndexed { index, answer ->
                    AnswerButton(
                        text = answer,
                        onClick = { onAnswerSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(4.dp),
            text = text,
            fontFamily = VazirFont,
            style = TextStyle(textDirection = TextDirection.Rtl),
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MyText(title: String, size: Int, isTitle: Boolean) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (isTitle) 25.dp else 8.dp),
        text = if (isTitle) "سوال $title" else title,
        fontFamily = if (isTitle) VazirFontBold else VazirFont,
        style = TextStyle(textDirection = TextDirection.Rtl),
        fontSize = size.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontWeight = if (isTitle) FontWeight.Bold else FontWeight.Medium
    )
}