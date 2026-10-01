package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SensorsOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeviceCategory
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.CardBorder
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteText
import com.example.ui.theme.RemoteTextDim
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated

@Composable
fun IrUnavailableDialog(
    category: DeviceCategory,
    onDismiss: () -> Unit,
    onOpenExternalAdapterGuide: () -> Unit,
    onProceedToEmulator: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ir_unavailable_dialog"),
        shape = RoundedCornerShape(24.dp),
        containerColor = SurfaceDark,
        tonalElevation = 8.dp,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Glow warning badge
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(AccentRed.copy(alpha = 0.25f), Color.Transparent)
                            )
                        )
                        .border(1.5.dp, AccentRed.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.SensorsOff,
                        contentDescription = "ИК-порт недоступен",
                        tint = AccentRed,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "ИК-порт не доступен",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = RemoteText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Категория: ${category.title}",
                    fontSize = 13.sp,
                    color = AccentCyan,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Notice box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceCard)
                        .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = AccentAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Аппаратное ограничение устройства",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AccentAmber
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "На данном устройстве отсутствует встроенный инфракрасный передатчик (IR Blaster). Прямая отправка ИК-команд на физическую технику невозможна.",
                            fontSize = 13.sp,
                            color = RemoteTextDim,
                            lineHeight = 18.sp
                        )
                    }
                }

                // External adapters option info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceElevated)
                        .border(1.dp, RemoteButtonBorder, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Cable,
                                contentDescription = null,
                                tint = AccentCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Поддержка сторонних излучателей",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AccentCyan
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Доступно подключение внешних ИК-модулей:\n• USB Type-C IR Dongle (OTG)\n• Аудио-ИК передатчик (разъем 3.5 мм)\n• Wi-Fi / Bluetooth ИК-мост",
                            fontSize = 12.sp,
                            color = RemoteText,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onProceedToEmulator,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("proceed_to_emulator_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentCyan,
                        contentColor = Color(0xFF090D16)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Эмуляция пульта (Демо-режим)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                OutlinedButton(
                    onClick = onOpenExternalAdapterGuide,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("open_external_adapter_button"),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(AccentCyan, AccentAmber))
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cable,
                        contentDescription = null,
                        tint = AccentCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Подключить внешний ИК-адаптер",
                        color = RemoteText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().testTag("close_dialog_button")
                ) {
                    Text(
                        text = "Закрыть",
                        color = RemoteTextDim,
                        fontSize = 13.sp
                    )
                }
            }
        },
        dismissButton = null
    )
}
