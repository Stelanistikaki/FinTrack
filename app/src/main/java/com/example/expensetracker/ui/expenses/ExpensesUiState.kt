package com.example.expensetracker.ui.expenses

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory

data class ExpensesUiState(
    val expenses: List<Expense> = emptyList(),
    val filteredExpenses: List<Expense> = emptyList(),
    val totalAmount: Double = 0.0,
    val selectedCategory: ExpenseCategory? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = true
)
