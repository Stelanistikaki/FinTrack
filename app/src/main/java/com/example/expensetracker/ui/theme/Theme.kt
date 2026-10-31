package com.example.expensetracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = RevolutBlue,
    onPrimary = Color.White,
    primaryContainer = RevolutDarkCard,
    onPrimaryContainer = Color.White,
    secondary = CategoryTransport,
    background = RevolutNavy,
    surface = RevolutNavy,
    surfaceVariant = RevolutDarkCard,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFF94A3B8),
    error = ExpenseRed
)

private val LightColorScheme = lightColorScheme(
    primary = RevolutBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEFF6FF),
    onPrimaryContainer = RevolutNavy,
    secondary = CategoryTransport,
    background = RevolutLightBg,
    surface = RevolutLightCard,
    surfaceVariant = RevolutSurfaceVariant,
    onSurface = RevolutNavy,
    onSurfaceVariant = Color(0xFF64748B),
    error = ExpenseRed
)

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to preserve our custom Revolut brand look
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
