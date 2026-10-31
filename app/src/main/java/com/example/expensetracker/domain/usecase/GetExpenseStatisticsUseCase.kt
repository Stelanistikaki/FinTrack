package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.CategoryExpenseSummary
import com.example.expensetracker.domain.model.ExpenseStatistics
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpenseStatisticsUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<ExpenseStatistics> {
        return repository.getAllExpenses().map { expenses ->
            if (expenses.isEmpty()) {
                return@map ExpenseStatistics()
            }

            val totalSpent = expenses.sumOf { it.amount }
            val totalCount = expenses.size
            val averageExpense = if (totalCount > 0) totalSpent / totalCount else 0.0
            val highestExpense = expenses.maxByOrNull { it.amount }

            val categoryGrouped = expenses.groupBy { it.category }
            val categoryBreakdown = categoryGrouped.map { (category, list) ->
                val categorySum = list.sumOf { it.amount }
                val percentage = if (totalSpent > 0) ((categorySum / totalSpent) * 100).toFloat() else 0f
                CategoryExpenseSummary(
                    category = category,
                    totalAmount = categorySum,
                    percentage = percentage
                )
            }.sortedByDescending { it.totalAmount }

            ExpenseStatistics(
                totalSpent = totalSpent,
                totalCount = totalCount,
                averageExpense = averageExpense,
                highestExpense = highestExpense,
                categoryBreakdown = categoryBreakdown
            )
        }
    }
}
