package com.example.expensetracker.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.usecase.DeleteExpenseUseCase
import com.example.expensetracker.domain.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            getExpensesUseCase().collect { expenses ->
                _uiState.update { currentState ->
                    val filtered = applyFilterAndSearch(
                        expenses = expenses,
                        category = currentState.selectedCategory,
                        query = currentState.searchQuery
                    )
                    currentState.copy(
                        expenses = expenses,
                        filteredExpenses = filtered,
                        totalAmount = expenses.sumOf { it.amount },
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onCategorySelected(category: ExpenseCategory?) {
        _uiState.update { currentState ->
            val newCategory = if (currentState.selectedCategory == category) null else category
            val filtered = applyFilterAndSearch(
                expenses = currentState.expenses,
                category = newCategory,
                query = currentState.searchQuery
            )
            currentState.copy(
                selectedCategory = newCategory,
                filteredExpenses = filtered
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { currentState ->
            val filtered = applyFilterAndSearch(
                expenses = currentState.expenses,
                category = currentState.selectedCategory,
                query = query
            )
            currentState.copy(
                searchQuery = query,
                filteredExpenses = filtered
            )
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            deleteExpenseUseCase(expense)
        }
    }

    private fun applyFilterAndSearch(
        expenses: List<Expense>,
        category: ExpenseCategory?,
        query: String
    ): List<Expense> {
        return expenses.filter { expense ->
            val matchesCategory = category == null || expense.category == category
            val matchesSearch = query.isBlank() ||
                    expense.title.contains(query, ignoreCase = true) ||
                    expense.note.contains(query, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }
}
