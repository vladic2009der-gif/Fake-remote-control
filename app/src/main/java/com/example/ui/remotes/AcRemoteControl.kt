package com.example.ui.remotes

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ModeFanOff
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TactileButton
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.LcdScreenBackground
import com.example.ui.theme.LcdText
import com.example.ui.theme.LcdTextDim
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteChassis

@Composable
fun AcRemoteControl(
    onSendCommand: (name: String, hexCode: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var temperature by remember { mutableIntStateOf(24) }
    var currentModeIndex by remember { mutableIntStateOf(0) }
    val modes = listOf("COOL ❄️", "HEAT ☀️", "DRY 💧", "FAN 💨", "AUTO 🔄")

    var fanSpeed by remember { mutableIntStateOf(2) } // 1..4
    var isSwingActive by remember { mutableStateOf(true) }
    var isPowerOn by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(RemoteChassis)
            .border(1.5.dp, RemoteButtonBorder, RoundedCornerShape(32.dp))
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // High-tech Retro Backlit LCD Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (isPowerOn) LcdScreenBackground else Color(0xFF152220))
                .border(2.dp, if (isPowerOn) Color(0xFF4E7D73) else Color(0xFF233632), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                // LCD top status line
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isPowerOn) modes[currentModeIndex] else "STANDBY",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPowerOn) LcdText else LcdTextDim
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isSwingActive) "SWING: ON" else "SWING: OFF",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = if (isPowerOn && isSwingActive) LcdText else LcdTextDim
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "FAN: $fanSpeed/4",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = if (isPowerOn) LcdText else LcdTextDim
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Big Temperature reading
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isPowerOn) "$temperature°C" else "--°C",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isPowerOn) LcdText else LcdTextDim
                    )

                    // Fan bars indicator
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        for (bar in 1..4) {
                            Box(
                                modifier = Modifier
                                    .width(6.dp)
                                    .height((bar * 7).dp)
                                    .background(
                                        if (isPowerOn && bar <= fanSpeed) LcdText else LcdTextDim
                                    )
                            )
                        }
                    }
                }
            }
        }

        // Power & Temp Up/Down
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TactileButton(
                onClick = {
                    isPowerOn = !isPowerOn
                    onSendCommand("AC POWER", if (isPowerOn) "0xDA4E817E" else "0xDA4E01FE")
                },
                icon = Icons.Default.PowerSettingsNew,
                accentColor = if (isPowerOn) AccentRed else Color(0xFF475569),
                iconTint = Color.White,
                subLabel = if (isPowerOn) "ON" else "OFF",
                size = 54.dp,
                testTag = "btn_ac_power"
            )

            // Temperature UP button
            TactileButton(
                onClick = {
                    if (temperature < 32) {
                        temperature++
                        onSendCommand("TEMP + ($temperature°C)", "0xDA4E${temperature}A1")
                    }
                },
                icon = Icons.Default.KeyboardArrowUp,
                subLabel = "TEMP +",
                size = 54.dp,
                testTag = "btn_ac_temp_up"
            )

            // Temperature DOWN button
            TactileButton(
                onClick = {
                    if (temperature > 16) {
                        temperature--
                        onSendCommand("TEMP - ($temperature°C)", "0xDA4E${temperature}B2")
                    }
                },
                icon = Icons.Default.KeyboardArrowDown,
                subLabel = "TEMP -",
                size = 54.dp,
                testTag = "btn_ac_temp_down"
            )
        }

        // Mode, Fan, Swing Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TactileButton(
                onClick = {
                    currentModeIndex = (currentModeIndex + 1) % modes.size
                    onSendCommand("MODE -> ${modes[currentModeIndex]}", "0xDA4EC${currentModeIndex}F0")
                },
                icon = Icons.Default.Sync,
                accentColor = AccentCyan,
                iconTint = Color(0xFF090D16),
                subLabel = "РЕЖИМ",
                size = 50.dp,
                testTag = "btn_ac_mode"
            )

            TactileButton(
                onClick = {
                    fanSpeed = if (fanSpeed >= 4) 1 else fanSpeed + 1
                    onSendCommand("FAN SPEED $fanSpeed", "0xDA4EF${fanSpeed}A2")
                },
                icon = Icons.Default.Air,
                subLabel = "СКОРОСТЬ",
                size = 50.dp,
                testTag = "btn_ac_fan"
            )

            TactileButton(
                onClick = {
                    isSwingActive = !isSwingActive
                    onSendCommand("SWING ${if (isSwingActive) "ON" else "OFF"}", "0xDA4E9966")
                },
                icon = Icons.Default.Speed,
                subLabel = "ЖАЛЮЗИ",
                size = 50.dp,
                testTag = "btn_ac_swing"
            )
        }

        // Turbo, Eco, Timer Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TactileButton(
                onClick = { onSendCommand("TURBO", "0xDA4EFF00") },
                label = "TURBO",
                size = 46.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_ac_turbo"
            )
            TactileButton(
                onClick = { onSendCommand("ECO SAVER", "0xDA4EAA55") },
                icon = Icons.Default.Eco,
                subLabel = "ЭКО",
                size = 46.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_ac_eco"
            )
            TactileButton(
                onClick = { onSendCommand("TIMER 1H", "0xDA4E7788") },
                label = "ТАЙМЕР",
                size = 46.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_ac_timer"
            )
        }
    }
}
