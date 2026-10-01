package com.example.ui.remotes

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TactileButton
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteChassis
import com.example.ui.theme.RemoteText
import com.example.ui.theme.SurfaceCard

@Composable
fun TvRemoteControl(
    onSendCommand: (name: String, hexCode: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showNumpad by remember { mutableStateOf(false) }

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
        // Top Row: Power & Source & Mute & Settings
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TactileButton(
                onClick = { onSendCommand("POWER", "0x20DF10EF") },
                icon = Icons.Default.PowerSettingsNew,
                accentColor = AccentRed,
                iconTint = Color.White,
                subLabel = "POWER",
                size = 50.dp,
                testTag = "btn_power"
            )

            TactileButton(
                onClick = { onSendCommand("INPUT / SOURCE", "0x20DFD02F") },
                icon = Icons.Default.Tv,
                accentColor = AccentCyan,
                iconTint = Color(0xFF090D16),
                subLabel = "SOURCE",
                size = 50.dp,
                testTag = "btn_source"
            )

            TactileButton(
                onClick = { onSendCommand("MUTE", "0x20DF906F") },
                icon = Icons.AutoMirrored.Filled.VolumeMute,
                subLabel = "MUTE",
                size = 50.dp,
                testTag = "btn_mute"
            )

            TactileButton(
                onClick = { onSendCommand("SETTINGS", "0x20DFC23D") },
                icon = Icons.Default.Settings,
                subLabel = "МЕНЮ",
                size = 50.dp,
                testTag = "btn_settings"
            )
        }

        // Circular D-Pad with OK
        Box(
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .background(SurfaceCard)
                .border(2.dp, RemoteButtonBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Up
            Box(modifier = Modifier.align(Alignment.TopCenter).padding(top = 10.dp)) {
                TactileButton(
                    onClick = { onSendCommand("UP", "0x20DF02FD") },
                    icon = Icons.Default.KeyboardArrowUp,
                    size = 46.dp,
                    testTag = "btn_up"
                )
            }
            // Down
            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp)) {
                TactileButton(
                    onClick = { onSendCommand("DOWN", "0x20DF827D") },
                    icon = Icons.Default.KeyboardArrowDown,
                    size = 46.dp,
                    testTag = "btn_down"
                )
            }
            // Left
            Box(modifier = Modifier.align(Alignment.CenterStart).padding(start = 10.dp)) {
                TactileButton(
                    onClick = { onSendCommand("LEFT", "0x20DFE01F") },
                    icon = Icons.Default.KeyboardArrowLeft,
                    size = 46.dp,
                    testTag = "btn_left"
                )
            }
            // Right
            Box(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp)) {
                TactileButton(
                    onClick = { onSendCommand("RIGHT", "0x20DF609F") },
                    icon = Icons.Default.KeyboardArrowRight,
                    size = 46.dp,
                    testTag = "btn_right"
                )
            }
            // Center OK button
            TactileButton(
                onClick = { onSendCommand("OK / ENTER", "0x20DF22DD") },
                label = "OK",
                accentColor = AccentCyan,
                iconTint = Color(0xFF090D16),
                size = 58.dp,
                testTag = "btn_ok"
            )
        }

        // Navigation Row: Back, Home, Numpad Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TactileButton(
                onClick = { onSendCommand("BACK", "0x20DF14EB") },
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                subLabel = "НАЗАД",
                size = 46.dp,
                testTag = "btn_back"
            )
            TactileButton(
                onClick = { onSendCommand("HOME", "0x20DF3EC1") },
                icon = Icons.Default.Home,
                subLabel = "ДОМОЙ",
                size = 46.dp,
                testTag = "btn_home"
            )
            TactileButton(
                onClick = { showNumpad = !showNumpad },
                icon = Icons.Default.Dialpad,
                accentColor = if (showNumpad) AccentCyan else null,
                iconTint = if (showNumpad) Color(0xFF090D16) else RemoteText,
                subLabel = if (showNumpad) "СКРЫТЬ" else "123",
                size = 46.dp,
                testTag = "btn_toggle_numpad"
            )
        }

        // Volume & Channel Rockers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Volume Rocker
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(SurfaceCard)
                    .border(1.dp, RemoteButtonBorder, RoundedCornerShape(24.dp))
                    .padding(vertical = 6.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TactileButton(
                    onClick = { onSendCommand("VOL +", "0x20DF40BF") },
                    icon = Icons.Default.Add,
                    size = 44.dp,
                    shape = RoundedCornerShape(16.dp),
                    testTag = "btn_vol_up"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "VOL",
                    fontSize = 11.sp,
                    color = AccentCyan,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TactileButton(
                    onClick = { onSendCommand("VOL -", "0x20DFC03F") },
                    icon = Icons.Default.Remove,
                    size = 44.dp,
                    shape = RoundedCornerShape(16.dp),
                    testTag = "btn_vol_down"
                )
            }

            // Quick App shortcuts (Netflix / YouTube / Browser)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TactileButton(
                    onClick = { onSendCommand("NETFLIX", "0x20DF8F70") },
                    label = "NETFLIX",
                    accentColor = Color(0xFFE50914),
                    iconTint = Color.White,
                    size = 42.dp,
                    shape = RoundedCornerShape(10.dp),
                    testTag = "btn_netflix"
                )
                TactileButton(
                    onClick = { onSendCommand("YOUTUBE", "0x20DFA05F") },
                    label = "YOUTUBE",
                    accentColor = Color(0xFFFF0000),
                    iconTint = Color.White,
                    size = 42.dp,
                    shape = RoundedCornerShape(10.dp),
                    testTag = "btn_youtube"
                )
            }

            // Channel Rocker
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(SurfaceCard)
                    .border(1.dp, RemoteButtonBorder, RoundedCornerShape(24.dp))
                    .padding(vertical = 6.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TactileButton(
                    onClick = { onSendCommand("CH +", "0x20DF00FF") },
                    icon = Icons.Default.KeyboardArrowUp,
                    size = 44.dp,
                    shape = RoundedCornerShape(16.dp),
                    testTag = "btn_ch_up"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "CH",
                    fontSize = 11.sp,
                    color = AccentCyan,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TactileButton(
                    onClick = { onSendCommand("CH -", "0x20DF807F") },
                    icon = Icons.Default.KeyboardArrowDown,
                    size = 44.dp,
                    shape = RoundedCornerShape(16.dp),
                    testTag = "btn_ch_down"
                )
            }
        }

        // Color buttons (Red, Green, Yellow, Blue)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TactileButton(
                onClick = { onSendCommand("RED KEY", "0x20DF4EB1") },
                accentColor = Color(0xFFEF4444),
                size = 28.dp,
                shape = RoundedCornerShape(6.dp),
                testTag = "btn_color_red"
            )
            TactileButton(
                onClick = { onSendCommand("GREEN KEY", "0x20DF8EB7") },
                accentColor = Color(0xFF10B981),
                size = 28.dp,
                shape = RoundedCornerShape(6.dp),
                testTag = "btn_color_green"
            )
            TactileButton(
                onClick = { onSendCommand("YELLOW KEY", "0x20DFC639") },
                accentColor = Color(0xFFF59E0B),
                size = 28.dp,
                shape = RoundedCornerShape(6.dp),
                testTag = "btn_color_yellow"
            )
            TactileButton(
                onClick = { onSendCommand("BLUE KEY", "0x20DF8679") },
                accentColor = Color(0xFF3B82F6),
                size = 28.dp,
                shape = RoundedCornerShape(6.dp),
                testTag = "btn_color_blue"
            )
        }

        // Animated Numeric Pad
        AnimatedVisibility(visible = showNumpad) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceCard)
                    .border(1.dp, RemoteButtonBorder, RoundedCornerShape(16.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val numRows = listOf(
                    listOf("1", "2", "3"),
                    listOf("4", "5", "6"),
                    listOf("7", "8", "9"),
                    listOf("INFO", "0", "CLEAR")
                )

                for (row in numRows) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (item in row) {
                            TactileButton(
                                onClick = { onSendCommand("NUM $item", "0x20DF${item.hashCode().toString(16).take(4).uppercase()}") },
                                label = item,
                                size = 42.dp,
                                shape = RoundedCornerShape(10.dp),
                                testTag = "btn_num_$item"
                            )
                        }
                    }
                }
            }
        }
    }
}
