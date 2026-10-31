package com.example.expensetracker.domain.model

data class Expense(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val date: Long = System.currentTimeMillis(),
    val note: String = ""
)
