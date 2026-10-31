package com.example.expensetracker.domain.model

data class CategoryExpenseSummary(
    val category: ExpenseCategory,
    val totalAmount: Double,
    val percentage: Float
)

data class ExpenseStatistics(
    val totalSpent: Double = 0.0,
    val totalCount: Int = 0,
    val averageExpense: Double = 0.0,
    val highestExpense: Expense? = null,
    val categoryBreakdown: List<CategoryExpenseSummary> = emptyList()
)
