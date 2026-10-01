package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PremiumDarkColorScheme = darkColorScheme(
    primary = AccentCyan,
    onPrimary = RemoteChassis,
    primaryContainer = SurfaceElevated,
    onPrimaryContainer = AccentCyan,
    secondary = AccentAmber,
    onSecondary = RemoteChassis,
    background = BackgroundDark,
    onBackground = RemoteText,
    surface = SurfaceDark,
    onSurface = RemoteText,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = RemoteTextDim,
    outline = CardBorder,
    error = AccentRed,
    onError = RemoteText
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = PremiumDarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = BackgroundDark.toArgb()
                window.navigationBarColor = BackgroundDark.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
