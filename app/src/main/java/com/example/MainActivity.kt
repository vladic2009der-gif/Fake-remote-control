package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.model.DeviceCategory
import com.example.ui.components.ExternalAdapterSheet
import com.example.ui.components.IrUnavailableDialog
import com.example.ui.screens.CategoryGridScreen
import com.example.ui.screens.RemoteEmulatorScreen
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundDark
                ) {
                    IrRemoteApp()
                }
            }
        }
    }
}

@Composable
fun IrRemoteApp() {
    // Current category chosen for the mandatory warning dialog
    var pendingCategoryForDialog by remember { mutableStateOf<DeviceCategory?>(null) }
    // Currently active category in remote control screen
    var activeRemoteCategory by remember { mutableStateOf<DeviceCategory?>(null) }
    // External adapter guide bottom sheet visibility
    var showAdapterGuide by remember { mutableStateOf(false) }

    Crossfade(targetState = activeRemoteCategory, label = "ScreenTransition") { currentCategory ->
        if (currentCategory == null) {
            CategoryGridScreen(
                onCategorySelected = { category ->
                    // ALWAYS show the IR unavailable notice regardless of category/device
                    pendingCategoryForDialog = category
                },
                onOpenAdapterGuide = {
                    showAdapterGuide = true
                }
            )
        } else {
            RemoteEmulatorScreen(
                category = currentCategory,
                onBack = {
                    activeRemoteCategory = null
                },
                onOpenAdapterGuide = {
                    showAdapterGuide = true
                }
            )
        }
    }

    // The mandatory notification dialog whenever ANY category is selected:
    pendingCategoryForDialog?.let { category ->
        IrUnavailableDialog(
            category = category,
            onDismiss = {
                pendingCategoryForDialog = null
            },
            onOpenExternalAdapterGuide = {
                pendingCategoryForDialog = null
                showAdapterGuide = true
            },
            onProceedToEmulator = {
                activeRemoteCategory = category
                pendingCategoryForDialog = null
            }
        )
    }

    // External adapter guide / scanner modal bottom sheet
    if (showAdapterGuide) {
        ExternalAdapterSheet(
            onDismiss = { showAdapterGuide = false },
            onOpenEmulator = {
                showAdapterGuide = false
                if (activeRemoteCategory == null && pendingCategoryForDialog != null) {
                    activeRemoteCategory = pendingCategoryForDialog
                    pendingCategoryForDialog = null
                } else if (activeRemoteCategory == null) {
                    // Default to first category (TV) if opened from main screen
                    activeRemoteCategory = com.example.model.DeviceCategories.allCategories.first()
                }
            }
        )
    }
}
