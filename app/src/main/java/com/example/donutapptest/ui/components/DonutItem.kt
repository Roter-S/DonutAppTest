package com.example.donutapptest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.ui.preview.SampleData
import java.util.Locale

@Composable
fun LabeledText(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                append("$label: ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                append(value)
            }
        },
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun DonutItem(
    donut: Donut,
    onFavoriteClick: (Boolean) -> Unit = {}
) {
    val isFavorite = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(donut.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = donut.name,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    error = rememberVectorPainter(Icons.Default.BrokenImage)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = donut.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledText(label = "Tipo", value = donut.type)

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "$${String.format(Locale.US, "%.2f", donut.ppu)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledText(
                        label = "Batters",
                        value = donut.batters.batter.joinToString(", ") { it.type }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    LabeledText(
                        label = "Toppings",
                        value = donut.topping.joinToString(", ") { it.type }
                    )
                }
            }

            IconButton(
                onClick = {
                    isFavorite.value = !isFavorite.value
                    onFavoriteClick(isFavorite.value)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite.value) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (isFavorite.value) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DonutItemPreview() {
    DonutItem(donut = SampleData.sampleDonut)
}