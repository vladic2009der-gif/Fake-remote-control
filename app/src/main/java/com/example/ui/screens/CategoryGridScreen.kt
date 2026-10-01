package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.DiscFull
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SensorsOff
import androidx.compose.material.icons.filled.SettingsRemote
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeviceCategories
import com.example.model.DeviceCategory
import com.example.ui.components.PromoBanner
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.CardBorder
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteText
import com.example.ui.theme.RemoteTextDim
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryGridScreen(
    onCategorySelected: (DeviceCategory) -> Unit,
    onOpenAdapterGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredCategories = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            DeviceCategories.allCategories
        } else {
            DeviceCategories.allCategories.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.subtitle.contains(searchQuery, ignoreCase = true) ||
                it.popularBrands.any { brand -> brand.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.linearGradient(listOf(AccentCyan, Color(0xFF1E40AF)))
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.SettingsRemote,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "IR REMOTE",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = RemoteText,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(AccentCyan.copy(alpha = 0.2f))
                                        .border(0.8.dp, AccentCyan, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "PRO",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AccentCyan
                                    )
                                }
                            }
                            Text(
                                text = "Универсальный ИК-пульт",
                                fontSize = 11.sp,
                                color = RemoteTextDim
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = onOpenAdapterGuide,
                        modifier = Modifier.testTag("top_adapter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cable,
                            contentDescription = "ИК-Адаптеры",
                            tint = AccentCyan
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Hardware Status Banner (Always informs IR is unavailable)
            item {
                HardwareStatusBar(
                    onClick = onOpenAdapterGuide,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Sponsored Ad Banner Spot
            item {
                PromoBanner(
                    onPromoClick = onOpenAdapterGuide
                )
            }

            // Search Bar
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("category_search_input"),
                        placeholder = {
                            Text(
                                text = "Поиск бренда или устройства (Samsung, Daikin, LG...)",
                                fontSize = 13.sp,
                                color = RemoteTextDim
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Поиск",
                                tint = RemoteTextDim
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceCard,
                            unfocusedContainerColor = SurfaceCard,
                            focusedBorderColor = AccentCyan,
                            unfocusedBorderColor = CardBorder,
                            focusedTextColor = RemoteText,
                            unfocusedTextColor = RemoteText
                        )
                    )
                }
            }

            // Section Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "КАТЕГОРИИ УСТРОЙСТВ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = RemoteTextDim,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${filteredCategories.size} групп",
                        fontSize = 12.sp,
                        color = AccentCyan
                    )
                }
            }

            // Categories List
            items(filteredCategories, key = { it.id }) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategorySelected(category) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun HardwareStatusBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF261014),
                        Color(0xFF1B1424)
                    )
                )
            )
            .border(1.dp, AccentRed.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp)
            .testTag("hardware_status_card")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(AccentRed.copy(alpha = 0.2f))
                    .border(1.dp, AccentRed.copy(alpha = 0.6f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.SensorsOff,
                    contentDescription = null,
                    tint = AccentRed,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Встроенный ИК-порт: Не обнаружен",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = RemoteText
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Требуется внешний модуль Type-C / 3.5mm или демо-режим",
                    fontSize = 11.sp,
                    color = AccentAmber
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = RemoteTextDim,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: DeviceCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (category.iconName) {
        "tv" -> Icons.Default.Tv
        "ac" -> Icons.Default.Air
        "projector" -> Icons.Default.Videocam
        "audio" -> Icons.Default.Speaker
        "stb" -> Icons.Default.Dns
        "fan" -> Icons.Default.Air
        "light" -> Icons.Default.Lightbulb
        "dvd" -> Icons.Default.DiscFull
        else -> Icons.Default.DeviceHub
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp)
            .testTag("category_card_${category.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon Badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceElevated)
                    .border(1.dp, RemoteButtonBorder, RoundedCornerShape(14.dp))
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = category.title,
                    tint = AccentCyan,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = category.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = RemoteText
                    )
                    Text(
                        text = category.deviceCountApprox,
                        fontSize = 11.sp,
                        color = AccentAmber
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = category.subtitle,
                    fontSize = 12.sp,
                    color = RemoteTextDim,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Popular brands preview tags
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (brand in category.popularBrands.take(3)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF0F172A))
                                .border(0.6.dp, Color(0xFF334155), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = brand,
                                fontSize = 10.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                    if (category.popularBrands.size > 3) {
                        Text(
                            text = "+${category.popularBrands.size - 3}",
                            fontSize = 10.sp,
                            color = AccentCyan,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color(0xFF475569),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
