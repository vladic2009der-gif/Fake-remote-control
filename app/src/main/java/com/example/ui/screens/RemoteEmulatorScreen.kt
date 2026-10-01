package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SensorsOff
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeviceCategory
import com.example.model.IrSignal
import com.example.model.RemoteType
import com.example.ui.components.IrDiodeIndicator
import com.example.ui.remotes.AcRemoteControl
import com.example.ui.remotes.AudioRemoteControl
import com.example.ui.remotes.ProjectorRemoteControl
import com.example.ui.remotes.TvRemoteControl
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteEmulatorScreen(
    category: DeviceCategory,
    onBack: () -> Unit,
    onOpenAdapterGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedBrand by remember { mutableStateOf(category.popularBrands.firstOrNull() ?: "Universal") }
    var isTransmitting by remember { mutableStateOf(false) }
    var lastSignal by remember { mutableStateOf<IrSignal?>(null) }
    var showConsoleLog by remember { mutableStateOf(false) }

    val signalLogs = remember { mutableStateListOf<IrSignal>() }

    // Auto turn off IR diode pulse after 280ms
    LaunchedEffect(isTransmitting) {
        if (isTransmitting) {
            delay(280)
            isTransmitting = false
        }
    }

    val onSendCommand: (name: String, hexCode: String) -> Unit = { name, hex ->
        val signal = IrSignal(
            commandName = name,
            hexCode = hex,
            protocol = category.supportedProtocols.firstOrNull() ?: "NEC",
            frequencyKHz = 38
        )
        lastSignal = signal
        isTransmitting = true
        signalLogs.add(0, signal)
        if (signalLogs.size > 15) {
            signalLogs.removeLast()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = category.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RemoteText
                        )
                        Text(
                            text = "Бренд: $selectedBrand • 38 kHz",
                            fontSize = 11.sp,
                            color = AccentCyan
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("remote_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад к категориям",
                            tint = RemoteText
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onOpenAdapterGuide,
                        modifier = Modifier.testTag("remote_adapter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cable,
                            contentDescription = "Адаптеры",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Simulated Physical IR Diode Bulb at the top
            IrDiodeIndicator(
                isTransmitting = isTransmitting,
                lastSignal = lastSignal
            )

            // Emulation Notice Badge (reiterating hardware IR blaster is missing)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF19111E))
                    .border(1.dp, Color(0xFF4A2535), RoundedCornerShape(12.dp))
                    .clickable { onOpenAdapterGuide() }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SensorsOff,
                            contentDescription = null,
                            tint = AccentRed,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Эмуляция пульта • ИК-порт не обнаружен",
                            fontSize = 11.sp,
                            color = RemoteText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = "Адаптер >",
                        fontSize = 11.sp,
                        color = AccentCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Brand Selector Ribbon
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "ПРОФИЛЬ БРЕНДА:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = RemoteTextDim,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )

                LazyRow(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(category.popularBrands) { brand ->
                        val isSelected = brand == selectedBrand
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) AccentCyan else SurfaceCard)
                                .border(
                                    1.dp,
                                    if (isSelected) AccentCyan else CardBorder,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedBrand = brand }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color(0xFF090D16),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = brand,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color(0xFF090D16) else RemoteText
                                )
                            }
                        }
                    }
                }
            }

            // Interactive Remote Chassis
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                when (category.remoteType) {
                    RemoteType.TV, RemoteType.GENERIC -> {
                        TvRemoteControl(
                            onSendCommand = onSendCommand
                        )
                    }
                    RemoteType.AC -> {
                        AcRemoteControl(
                            onSendCommand = onSendCommand
                        )
                    }
                    RemoteType.PROJECTOR -> {
                        ProjectorRemoteControl(
                            onSendCommand = onSendCommand
                        )
                    }
                    RemoteType.AUDIO -> {
                        AudioRemoteControl(
                            onSendCommand = onSendCommand
                        )
                    }
                }
            }

            // Signal Protocol Console Log Toggle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceCard)
                    .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                    .clickable { showConsoleLog = !showConsoleLog }
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = null,
                            tint = AccentCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Лог отправленных сигналов (${signalLogs.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RemoteText
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = RemoteTextDim,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Expandable IR Console
            AnimatedVisibility(visible = showConsoleLog) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF060910))
                        .border(1.dp, RemoteButtonBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (signalLogs.isEmpty()) {
                        Text(
                            text = "Команды еще не отправлялись. Нажмите любую кнопку на пульте.",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = RemoteTextDim
                        )
                    } else {
                        signalLogs.take(8).forEach { sig ->
                            Text(
                                text = "> [${sig.protocol}] ${sig.commandName.padEnd(16)} | HEX: ${sig.hexCode} | 38kHz (EMULATED)",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = AccentCyan
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
