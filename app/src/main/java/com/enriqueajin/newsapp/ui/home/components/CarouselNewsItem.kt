package com.enriqueajin.newsapp.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.ui.theme.Purple80

@Composable
fun CarouselNewsItem(title: String, datetime: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Purple80,
            contentColor = Color.White
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 40.dp,
                    top = 20.dp,
                    bottom = 20.dp
                ), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            )
            Text(
                text = datetime,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarouselNewsItemPreview() {
    CarouselNewsItem("Noticia de última hora", "17 de mayo de 2024")
}