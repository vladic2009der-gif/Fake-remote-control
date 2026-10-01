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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Speaker
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TactileButton
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteChassis
import com.example.ui.theme.SurfaceCard

@Composable
fun AudioRemoteControl(
    onSendCommand: (name: String, hexCode: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var volume by remember { mutableIntStateOf(35) }
    var selectedInput by remember { mutableStateOf("OPTICAL") }

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
        // Power & Mute
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TactileButton(
                onClick = { onSendCommand("AUDIO POWER", "0x5EA1F00F") },
                icon = Icons.Default.PowerSettingsNew,
                accentColor = AccentRed,
                iconTint = Color.White,
                subLabel = "ПИТАНИЕ",
                size = 52.dp,
                testTag = "btn_audio_power"
            )

            TactileButton(
                onClick = { onSendCommand("AUDIO MUTE", "0x5EA1D02F") },
                icon = Icons.AutoMirrored.Filled.VolumeMute,
                subLabel = "MUTE",
                size = 52.dp,
                testTag = "btn_audio_mute"
            )

            TactileButton(
                onClick = { onSendCommand("PURE DIRECT", "0x5EA130CF") },
                label = "DIRECT",
                accentColor = AccentCyan,
                iconTint = Color(0xFF090D16),
                subLabel = "HI-FI",
                size = 52.dp,
                testTag = "btn_audio_direct"
            )
        }

        // Input Selector Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceCard)
                .border(1.dp, RemoteButtonBorder, RoundedCornerShape(16.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "ВЫБОР ВХОДА (АКТИВЕН: $selectedInput)",
                fontSize = 11.sp,
                color = AccentCyan,
                modifier = Modifier.padding(start = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val inputs = listOf(
                    Triple("OPTICAL", Icons.Default.GraphicEq, "0x5EA110EF"),
                    Triple("HDMI ARC", Icons.Default.Cable, "0x5EA1906F"),
                    Triple("BT", Icons.Default.Bluetooth, "0x5EA150AF"),
                    Triple("AUX", Icons.Default.Speaker, "0x5EA1B04F")
                )

                for ((name, icon, hex) in inputs) {
                    TactileButton(
                        onClick = {
                            selectedInput = name
                            onSendCommand("INPUT: $name", hex)
                        },
                        icon = icon,
                        accentColor = if (selectedInput == name) AccentCyan else null,
                        iconTint = if (selectedInput == name) Color(0xFF090D16) else Color.White,
                        subLabel = name,
                        size = 46.dp,
                        shape = RoundedCornerShape(10.dp),
                        testTag = "btn_input_$name"
                    )
                }
            }
        }

        // Master Volume & Tone Control
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Volume
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceCard)
                    .border(1.dp, RemoteButtonBorder, RoundedCornerShape(20.dp))
                    .padding(8.dp)
            ) {
                TactileButton(
                    onClick = {
                        if (volume < 100) volume += 2
                        onSendCommand("VOLUME + ($volume)", "0x5EA1E01F")
                    },
                    icon = Icons.AutoMirrored.Filled.VolumeUp,
                    size = 48.dp,
                    shape = RoundedCornerShape(12.dp),
                    testTag = "btn_audio_vol_up"
                )
                Text(
                    text = "VOL $volume",
                    fontSize = 11.sp,
                    color = AccentCyan,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                TactileButton(
                    onClick = {
                        if (volume > 0) volume -= 2
                        onSendCommand("VOLUME - ($volume)", "0x5EA1609F")
                    },
                    icon = Icons.AutoMirrored.Filled.VolumeDown,
                    size = 48.dp,
                    shape = RoundedCornerShape(12.dp),
                    testTag = "btn_audio_vol_down"
                )
            }

            // Bass & Treble
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TactileButton(
                        onClick = { onSendCommand("BASS +", "0x5EA18877") },
                        label = "BASS +",
                        size = 44.dp,
                        shape = RoundedCornerShape(10.dp),
                        testTag = "btn_bass_up"
                    )
                    TactileButton(
                        onClick = { onSendCommand("BASS -", "0x5EA108F7") },
                        label = "BASS -",
                        size = 44.dp,
                        shape = RoundedCornerShape(10.dp),
                        testTag = "btn_bass_down"
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TactileButton(
                        onClick = { onSendCommand("TREBLE +", "0x5EA148B7") },
                        label = "TREBLE +",
                        size = 44.dp,
                        shape = RoundedCornerShape(10.dp),
                        testTag = "btn_treble_up"
                    )
                    TactileButton(
                        onClick = { onSendCommand("TREBLE -", "0x5EA1C837") },
                        label = "TREBLE -",
                        size = 44.dp,
                        shape = RoundedCornerShape(10.dp),
                        testTag = "btn_treble_down"
                    )
                }
            }
        }

        // Playback row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TactileButton(
                onClick = { onSendCommand("PREV TRACK", "0x5EA128D7") },
                icon = Icons.Default.FastRewind,
                size = 46.dp,
                testTag = "btn_audio_prev"
            )
            TactileButton(
                onClick = { onSendCommand("PLAY / PAUSE", "0x5EA1A857") },
                icon = Icons.Default.PlayArrow,
                accentColor = AccentCyan,
                iconTint = Color(0xFF090D16),
                size = 46.dp,
                testTag = "btn_audio_play"
            )
            TactileButton(
                onClick = { onSendCommand("NEXT TRACK", "0x5EA16897") },
                icon = Icons.Default.FastForward,
                size = 46.dp,
                testTag = "btn_audio_next"
            )
        }
    }
}
