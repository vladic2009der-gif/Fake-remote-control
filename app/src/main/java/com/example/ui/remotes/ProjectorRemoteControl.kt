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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.material.icons.filled.CropLandscape
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.SettingsInputHdmi
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TactileButton
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteChassis
import com.example.ui.theme.SurfaceCard

@Composable
fun ProjectorRemoteControl(
    onSendCommand: (name: String, hexCode: String) -> Unit,
    modifier: Modifier = Modifier
) {
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
        // Top Row: Power & Source
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TactileButton(
                onClick = { onSendCommand("PROJECTOR POWER", "0x4CB340BF") },
                icon = Icons.Default.PowerSettingsNew,
                accentColor = AccentRed,
                iconTint = Color.White,
                subLabel = "ПИТАНИЕ",
                size = 52.dp,
                testTag = "btn_proj_power"
            )

            TactileButton(
                onClick = { onSendCommand("HDMI 1", "0x4CB320DF") },
                icon = Icons.Default.SettingsInputHdmi,
                accentColor = AccentCyan,
                iconTint = Color(0xFF090D16),
                subLabel = "HDMI 1",
                size = 52.dp,
                testTag = "btn_proj_hdmi1"
            )

            TactileButton(
                onClick = { onSendCommand("HDMI 2", "0x4CB3A05F") },
                icon = Icons.Default.SettingsInputHdmi,
                subLabel = "HDMI 2",
                size = 52.dp,
                testTag = "btn_proj_hdmi2"
            )

            TactileButton(
                onClick = { onSendCommand("FREEZE FRAME", "0x4CB3609F") },
                icon = Icons.Default.Pause,
                subLabel = "СТОП-КАДР",
                size = 52.dp,
                testTag = "btn_proj_freeze"
            )
        }

        // Keystone & Focus Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TactileButton(
                onClick = { onSendCommand("KEYSTONE +", "0x4CB310EF") },
                icon = Icons.Default.CropLandscape,
                subLabel = "ТРАПЕЦИЯ +",
                size = 48.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_proj_keystone_up"
            )

            TactileButton(
                onClick = { onSendCommand("KEYSTONE -", "0x4CB3906F") },
                icon = Icons.Default.CropPortrait,
                subLabel = "ТРАПЕЦИЯ -",
                size = 48.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_proj_keystone_down"
            )

            TactileButton(
                onClick = { onSendCommand("FOCUS +", "0x4CB350AF") },
                icon = Icons.Default.CenterFocusStrong,
                subLabel = "ФОКУС +",
                size = 48.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_proj_focus_plus"
            )

            TactileButton(
                onClick = { onSendCommand("FOCUS -", "0x4CB3D02F") },
                icon = Icons.Default.CenterFocusWeak,
                subLabel = "ФОКУС -",
                size = 48.dp,
                shape = RoundedCornerShape(12.dp),
                testTag = "btn_proj_focus_minus"
            )
        }

        // D-Pad
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(SurfaceCard)
                .border(2.dp, RemoteButtonBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp)) {
                TactileButton(
                    onClick = { onSendCommand("PROJ UP", "0x4CB302FD") },
                    icon = Icons.Default.KeyboardArrowUp,
                    size = 44.dp,
                    testTag = "btn_proj_up"
                )
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)) {
                TactileButton(
                    onClick = { onSendCommand("PROJ DOWN", "0x4CB3827D") },
                    icon = Icons.Default.KeyboardArrowDown,
                    size = 44.dp,
                    testTag = "btn_proj_down"
                )
            }
            Box(modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp)) {
                TactileButton(
                    onClick = { onSendCommand("PROJ LEFT", "0x4CB3E01F") },
                    icon = Icons.Default.KeyboardArrowLeft,
                    size = 44.dp,
                    testTag = "btn_proj_left"
                )
            }
            Box(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)) {
                TactileButton(
                    onClick = { onSendCommand("PROJ RIGHT", "0x4CB3609F") },
                    icon = Icons.Default.KeyboardArrowRight,
                    size = 44.dp,
                    testTag = "btn_proj_right"
                )
            }
            TactileButton(
                onClick = { onSendCommand("PROJ ENTER", "0x4CB322DD") },
                label = "ENTER",
                accentColor = AccentAmber,
                iconTint = Color(0xFF090D16),
                size = 54.dp,
                testTag = "btn_proj_enter"
            )
        }

        // Bottom Controls: Menu, Aspect, Blank, Back
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TactileButton(
                onClick = { onSendCommand("MENU", "0x4CB3B04F") },
                label = "МЕНЮ",
                size = 46.dp,
                testTag = "btn_proj_menu"
            )
            TactileButton(
                onClick = { onSendCommand("ASPECT 16:9", "0x4CB3708F") },
                icon = Icons.Default.AspectRatio,
                subLabel = "ФОРМАТ",
                size = 46.dp,
                testTag = "btn_proj_aspect"
            )
            TactileButton(
                onClick = { onSendCommand("BLANK SCREEN", "0x4CB3F00F") },
                label = "BLANK",
                size = 46.dp,
                testTag = "btn_proj_blank"
            )
            TactileButton(
                onClick = { onSendCommand("BACK", "0x4CB3F807") },
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                subLabel = "ВЫХОД",
                size = 46.dp,
                testTag = "btn_proj_back"
            )
        }
    }
}
