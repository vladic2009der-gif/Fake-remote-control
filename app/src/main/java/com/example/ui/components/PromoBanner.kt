package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.CardBorder
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteText
import com.example.ui.theme.RemoteTextDim
import com.example.ui.theme.SurfaceCard

@Composable
fun PromoBanner(
    modifier: Modifier = Modifier,
    onPromoClick: () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF141D2E),
                            Color(0xFF1E283D),
                            Color(0xFF121B2A)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        listOf(AccentCyan.copy(alpha = 0.5f), AccentAmber.copy(alpha = 0.5f))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onPromoClick() }
                .padding(12.dp)
                .testTag("promo_banner_card")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Sponsored / Ad Badge
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(AccentAmber.copy(alpha = 0.2f))
                                .border(1.dp, AccentAmber.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "РЕКЛАМА / СПОНСОР",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentAmber,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = AccentAmber,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "PRO BLASTER",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RemoteText
                            )
                        }
                    }

                    // Close banner button
                    IconButton(
                        onClick = { isVisible = false },
                        modifier = Modifier
                            .size(24.dp)
                            .testTag("close_promo_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Скрыть рекламу",
                            tint = RemoteTextDim,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AccentCyan.copy(alpha = 0.15f))
                            .border(1.dp, AccentCyan.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ElectricBolt,
                            contentDescription = null,
                            tint = AccentCyan,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Компактный Type-C ИК-излучатель",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = RemoteText
                        )
                        Text(
                            text = "Скидка 40% на внешние модули для смартфона без ИК",
                            fontSize = 11.sp,
                            color = RemoteTextDim,
                            lineHeight = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onPromoClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentCyan,
                            contentColor = Color(0xFF090D16)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "Купить",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
