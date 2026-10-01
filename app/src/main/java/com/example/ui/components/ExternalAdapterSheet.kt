package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentRed
import com.example.ui.theme.CardBorder
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteText
import com.example.ui.theme.RemoteTextDim
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalAdapterSheet(
    onDismiss: () -> Unit,
    onOpenEmulator: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isScanning by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "radar")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarRotate"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceDark,
        dragHandle = null,
        modifier = Modifier.testTag("external_adapter_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Drag indicator bar
            Box(
                modifier = Modifier
                    .size(width = 44.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF334155))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Icon with radar ring
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(SurfaceElevated)
                    .border(2.dp, AccentCyan, CircleShape)
            ) {
                if (isScanning) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(60.dp),
                        color = AccentCyan,
                        strokeWidth = 2.dp
                    )
                }
                Icon(
                    imageVector = Icons.Default.Sensors,
                    contentDescription = null,
                    tint = AccentCyan,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Подключение внешнего ИК-адаптера",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = RemoteText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Поддержка сторонних аппаратных модулей",
                fontSize = 13.sp,
                color = AccentAmber,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Scanner Status Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceCard)
                    .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ДИАГНОСТИКА ПОРТОВ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = RemoteTextDim,
                            letterSpacing = 1.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isScanning) AccentAmber else AccentRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isScanning) "ПОИСК..." else "НЕТ УСТРОЙСТВА",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = if (isScanning) AccentAmber else AccentRed,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Port items
                    AdapterPortRow(
                        icon = Icons.Default.Usb,
                        title = "USB Type-C OTG",
                        status = "Донгл не подключен",
                        isReady = false
                    )

                    AdapterPortRow(
                        icon = Icons.Default.Headphones,
                        title = "3.5 мм Audio Jack",
                        status = "ИК-излучатель не обнаружен",
                        isReady = false
                    )

                    AdapterPortRow(
                        icon = Icons.Default.Cable,
                        title = "Встроенный Consumer IR",
                        status = "Аппаратно не поддерживается",
                        isReady = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Step instructions
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceElevated)
                    .border(1.dp, RemoteButtonBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Как подключить внешний модуль:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = RemoteText
                    )
                    Text(
                        text = "1. Приобретите миниатюрный USB-C или 3.5мм IR Blaster.\n2. Вставьте адаптер в разъем смартфона.\n3. Разрешите приложению передачу OTG-сигнала.\n4. Тестируйте отправку кодов через панель пульта.",
                        fontSize = 12.sp,
                        color = RemoteTextDim,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Scan button
            Button(
                onClick = { isScanning = !isScanning },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scan_hardware_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceCard,
                    contentColor = AccentCyan
                ),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(AccentCyan, AccentAmber))
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isScanning) "Остановить сканирование портов" else "Повторить поиск ИК-адаптера",
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Proceed to imitation
            Button(
                onClick = {
                    onDismiss()
                    onOpenEmulator()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("open_emulator_from_sheet"),
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
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Перейти к пульту (Режим эмуляции)",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun AdapterPortRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    status: String,
    isReady: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0C1322))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF64748B),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = RemoteText
                )
                Text(
                    text = status,
                    fontSize = 11.sp,
                    color = RemoteTextDim
                )
            }
        }
        Icon(
            imageVector = if (isReady) Icons.Default.CheckCircle else Icons.Default.Sensors,
            contentDescription = null,
            tint = if (isReady) AccentGreen else Color(0xFF475569),
            modifier = Modifier.size(16.dp)
        )
    }
}
