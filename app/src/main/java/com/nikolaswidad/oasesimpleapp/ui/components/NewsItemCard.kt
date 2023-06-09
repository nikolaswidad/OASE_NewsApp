package com.nikolaswidad.oasesimpleapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nikolaswidad.oasesimpleapp.R
import com.nikolaswidad.oasesimpleapp.ui.theme.Shapes

@Composable
fun NewsItemCard(
//    news: News,
//    id: Int,
    title: String,
    image: String,
    url: String,
    source: String,
    publishedAt: String,
    credibilityScore: Int,
    sentiment: String,
    summary: String,
    bookmarked: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // make clickable
            }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp)
        ) {
            // Row News
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                        .weight(1.0f)
                ) {
                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 100.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(height = 10.dp)
                    )

                    Text(
                        text = publishedAt,
                        maxLines = 1,
                        fontWeight = FontWeight.Light
                    )

                }
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(Shapes.small)

                )
            }
            Spacer(
                modifier = Modifier
                    .height(height = 8.dp)
            )
            // Kolom Fitur
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Row Sentiment Start
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = when (sentiment) {
                            "positive" -> painterResource(R.drawable.ic_sentiment_negative)
                            "neutral" -> painterResource(R.drawable.ic_sentiment_neutral)
                            else -> painterResource(R.drawable.ic_sentiment_positive)
                        },
                        contentDescription = null,
                        modifier = Modifier

                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = 2.dp)
                    )
                    Text(
                        text = stringResource(R.string.tv_sentiment),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = 2.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_info),
                        contentDescription = "info",
                        modifier = Modifier
                            .size(size = 12.dp)
                    )
                }
                // Row Sentiment End

                // Row Verified Start
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        //                    painter = painterResource(R.drawable.ic_credibility_verified),

                        painter = if (credibilityScore == 0)
                            painterResource(R.drawable.ic_credibility_warning)
                        else
                            painterResource(R.drawable.ic_credibility_verified),
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = 2.dp)
                    )
                    Text(
                        text = "${credibilityScore}%",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = 2.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.tv_accurate_score),
                            fontSize = 7.sp,
                            lineHeight = 8.sp
                        )
                    }
                }
                // Row Verified End

                Image(
                    painter = if (bookmarked)
                        painterResource(R.drawable.ic_bookmarked)
                    else
                        painterResource(R.drawable.ic_bookmark),
                    contentDescription = null
                )

            }
        }
    }
}

@Preview
@Composable
fun NewsItemCardPreview() {
    NewsItemCard(
        "Breaking News",
        "CNN",
        "https://developer.android.com/jetpack/compose/layouts/material",
        "Kompas",
        "2023-06-09",
        90,
        "positive",
        "",
        false,
    )
}