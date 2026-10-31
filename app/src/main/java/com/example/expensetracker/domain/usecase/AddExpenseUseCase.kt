package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        require(expense.title.isNotBlank()) { "Expense title cannot be empty" }
        require(expense.amount > 0) { "Expense amount must be greater than zero" }

        if (expense.id == 0L) {
            repository.insertExpense(expense)
        } else {
            repository.updateExpense(expense)
        }
    }
}
