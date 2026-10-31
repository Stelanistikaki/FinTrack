package com.example.expensetracker.domain.model

enum class ExpenseCategory(val displayName: String) {
    FOOD("Food & Dining"),
    TRANSPORT("Transport"),
    SHOPPING("Shopping"),
    ENTERTAINMENT("Entertainment"),
    UTILITIES("Utilities & Bills"),
    HEALTH("Health & Fitness"),
    OTHER("Other");

    companion object {
        fun fromString(value: String): ExpenseCategory {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: OTHER
        }
    }
}
