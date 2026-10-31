package com.example.expensetracker.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.expensetracker.domain.model.ExpenseCategory

fun ExpenseCategory.getIcon(): ImageVector {
    return when (this) {
        ExpenseCategory.FOOD -> Icons.Default.Restaurant
        ExpenseCategory.TRANSPORT -> Icons.Default.DirectionsBus
        ExpenseCategory.SHOPPING -> Icons.Default.ShoppingBag
        ExpenseCategory.ENTERTAINMENT -> Icons.Default.Movie
        ExpenseCategory.UTILITIES -> Icons.AutoMirrored.Filled.ReceiptLong
        ExpenseCategory.HEALTH -> Icons.Default.MedicalServices
        ExpenseCategory.OTHER -> Icons.Default.MoreHoriz
    }
}

fun ExpenseCategory.getColor(): Color {
    return when (this) {
        ExpenseCategory.FOOD -> CategoryFood
        ExpenseCategory.TRANSPORT -> CategoryTransport
        ExpenseCategory.SHOPPING -> CategoryShopping
        ExpenseCategory.ENTERTAINMENT -> CategoryEntertainment
        ExpenseCategory.UTILITIES -> CategoryUtilities
        ExpenseCategory.HEALTH -> CategoryHealth
        ExpenseCategory.OTHER -> CategoryOther
    }
}

fun ExpenseCategory.getContainerColor(): Color {
    return getColor().copy(alpha = 0.15f)
}
