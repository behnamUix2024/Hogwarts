package com.behnamuix.hogwarts.View.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.behnamuix.hogwarts.Harrypoter.groups.Gryffindor
import com.behnamuix.hogwarts.Harrypoter.groups.Hufflepuff
import com.behnamuix.hogwarts.Harrypoter.groups.Ravenclaw
import com.behnamuix.hogwarts.Harrypoter.groups.Slytherin
import com.behnamuix.hogwarts.R

// ✅ مدل داده
data class HouseData(
    val name: String,
    val color: String,
    val element: String,
    val animal: String,
    val feature: String,
    val imageUrl: String
)

// ✅ کامپوننت اصلی صفحه
@Composable
fun GroupDetailContent() {
    Column(
        modifier = Modifier
            .paint(
                painterResource(R.drawable.bg2),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BackButton()
        Column(modifier =
            Modifier
                .padding(20.dp)) {
            MyText("ببینیم هر گروه چه خصوصیات خاصی دارن!",26)

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            HousesLazyRow()
        }
    }
}

// ✅ LazyRow برای نمایش اطلاعات گروه‌ها
@Composable
fun HousesLazyRow() {
    val houses = listOf(
        HouseData(
            name = Gryffindor().getHouseName(),
            color = Gryffindor().getColor(),
            element = Gryffindor().getElement(),
            animal = Gryffindor().getAnimal(),
            feature = Gryffindor().getFeature(),
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/1-16.jpg"
        ),
        HouseData(
            name = Slytherin().getHouseName(),
            color = Slytherin().getColor(),
            element = Slytherin().getElement(),
            animal = Slytherin().getAnimal(),
            feature = Slytherin().getFeature(),
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/2-18.jpg"
        ),
        HouseData(
            name = Ravenclaw().getHouseName(),
            color = Ravenclaw().getColor(),
            element = Ravenclaw().getElement(),
            animal = Ravenclaw().getAnimal(),
            feature = Ravenclaw().getFeature(),
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/3-11.jpg"
        ),
        HouseData(
            name = Hufflepuff().getHouseName(),
            color = Hufflepuff().getColor(),
            element = Hufflepuff().getElement(),
            animal = Hufflepuff().getAnimal(),
            feature = Hufflepuff().getFeature(),
            imageUrl = "https://vigiato.net/wp-content/uploads/2023/02/4-2.jpg"
        )
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(houses) { house ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xDA232323)),
                modifier = Modifier
                    .width(280.dp)
                    .padding(vertical = 18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column {
                    Image(
                        painter = rememberAsyncImagePainter(house.imageUrl),
                        contentDescription = house.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                    Column(Modifier.padding(16.dp)) {
                        MyText("پایه‌گذار: ${house.name}", size = 18)
                        MyText("رنگ: ${house.color}", size = 18)
                        MyText("عنصر: ${house.element}", size = 18)
                        MyText("حیوان: ${house.animal}", size = 18)
                        MyText("خصوصیات: ${house.feature}", size = 18)
                    }
                }
            }
        }
    }
}

// ✅ تابع برای نمایش متن فارسی
@Composable
fun MyText(title: String, size: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        text = title,
        fontFamily = VazirFont,
        style = TextStyle(textDirection = TextDirection.Rtl),
        fontSize = size.sp,
        color = Color.White,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Medium
    )
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
