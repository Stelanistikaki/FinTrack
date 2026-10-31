package com.example.expensetracker.ui.add_edit

import com.example.expensetracker.domain.model.ExpenseCategory

data class AddEditExpenseUiState(
    val id: Long = 0,
    val title: String = "",
    val amount: String = "",
    val category: ExpenseCategory = ExpenseCategory.FOOD,
    val note: String = "",
    val date: Long = System.currentTimeMillis(),
    val titleError: String? = null,
    val amountError: String? = null,
    val isSaved: Boolean = false,
    val isLoading: Boolean = false
)
