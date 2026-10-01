package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RemoteButtonBase
import com.example.ui.theme.RemoteButtonBorder
import com.example.ui.theme.RemoteButtonPressed
import com.example.ui.theme.RemoteText
import com.example.ui.theme.RemoteTextDim

@Composable
fun TactileButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    subLabel: String? = null,
    icon: ImageVector? = null,
    iconTint: Color = RemoteText,
    accentColor: Color? = null,
    shape: Shape = CircleShape,
    size: Dp = 54.dp,
    testTag: String = "tactile_button"
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = tween(durationMillis = 80),
        label = "btnScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size)
                .scale(scale)
                .shadow(
                    elevation = if (isPressed) 1.dp else 4.dp,
                    shape = shape,
                    clip = false
                )
                .clip(shape)
                .background(
                    if (accentColor != null) {
                        Brush.verticalGradient(
                            listOf(
                                accentColor.copy(alpha = if (isPressed) 0.7f else 0.9f),
                                accentColor.copy(alpha = if (isPressed) 0.5f else 0.7f)
                            )
                        )
                    } else {
                        Brush.verticalGradient(
                            listOf(
                                if (isPressed) RemoteButtonPressed else Color(0xFF223049),
                                if (isPressed) Color(0xFF141E30) else RemoteButtonBase
                            )
                        )
                    }
                )
                .border(
                    width = 1.2.dp,
                    color = accentColor?.copy(alpha = 0.5f) ?: if (isPressed) Color(0xFF475569) else RemoteButtonBorder,
                    shape = shape
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
                .testTag(testTag)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = label ?: "button",
                    tint = iconTint,
                    modifier = Modifier.size(size * 0.44f)
                )
            } else if (label != null) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconTint
                )
            }
        }

        if (subLabel != null) {
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subLabel,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = RemoteTextDim
            )
        }
    }
}
