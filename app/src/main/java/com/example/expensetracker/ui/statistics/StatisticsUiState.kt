package com.example.expensetracker.ui.statistics

import com.example.expensetracker.domain.model.ExpenseStatistics

data class StatisticsUiState(
    val statistics: ExpenseStatistics = ExpenseStatistics(),
    val isLoading: Boolean = true
)
