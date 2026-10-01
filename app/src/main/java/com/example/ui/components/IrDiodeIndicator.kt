package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.IrSignal
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentRed
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.SurfaceCard

@Composable
fun IrDiodeIndicator(
    isTransmitting: Boolean,
    lastSignal: IrSignal?,
    modifier: Modifier = Modifier
) {
    val diodeColor by animateColorAsState(
        targetValue = if (isTransmitting) AccentRed else Color(0xFF334155),
        animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing),
        label = "diodeColor"
    )

    val glowSize by animateDpAsState(
        targetValue = if (isTransmitting) 28.dp else 12.dp,
        animationSpec = tween(durationMillis = 150),
        label = "glowSize"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Physical Diode Bulb at the top rim of the remote
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(34.dp)
                .testTag("ir_diode_container")
        ) {
            // Ambient glow
            if (isTransmitting) {
                Box(
                    modifier = Modifier
                        .size(glowSize)
                        .clip(CircleShape)
                        .background(AccentRed.copy(alpha = 0.45f))
                )
            }

            // Glass bulb housing
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                diodeColor,
                                Color(0xFF1E293B)
                            )
                        )
                    )
                    .border(1.5.dp, if (isTransmitting) AccentRed else RemoteButtonBorder, CircleShape)
            ) {
                // Diode filament
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(if (isTransmitting) Color.White else Color(0xFF64748B))
                )
            }
        }

        // Live HUD Protocol Status Bar
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceCard)
                .border(1.dp, RemoteButtonBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color(0xFFF59E0B),
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (lastSignal != null && isTransmitting) {
                    "ИМПУЛЬС: [${lastSignal.protocol}] ${lastSignal.commandName} (${lastSignal.hexCode})"
                } else if (lastSignal != null) {
                    "ПОСЛЕДНИЙ: [${lastSignal.protocol}] ${lastSignal.commandName} • 38 kHz (СИМУЛЯЦИЯ)"
                } else {
                    "РЕЖИМ ЭМУЛЯЦИИ • ИК-ПОРТ НЕ ПОДКЛЮЧЕН"
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Monospace,
                color = if (isTransmitting) AccentCyan else Color(0xFF94A3B8)
            )
        }
    }
}
