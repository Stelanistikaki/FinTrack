package com.example.expensetracker.ui.add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import com.example.expensetracker.domain.usecase.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditExpenseUiState())
    val uiState: StateFlow<AddEditExpenseUiState> = _uiState.asStateFlow()

    init {
        val expenseIdStr: String? = savedStateHandle.get<String>("expenseId")
        val expenseId = expenseIdStr?.toLongOrNull() ?: -1L
        if (expenseId > 0) {
            loadExpense(expenseId)
        }
    }

    private fun loadExpense(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val expense = repository.getExpenseById(id)
            if (expense != null) {
                _uiState.update {
                    it.copy(
                        id = expense.id,
                        title = expense.title,
                        amount = expense.amount.toString(),
                        category = expense.category,
                        note = expense.note,
                        date = expense.date,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onTitleChanged(title: String) {
        _uiState.update {
            it.copy(
                title = title,
                titleError = if (title.isBlank()) "Title is required" else null
            )
        }
    }

    fun onAmountChanged(amount: String) {
        val parsed = amount.toDoubleOrNull()
        _uiState.update {
            it.copy(
                amount = amount,
                amountError = when {
                    amount.isBlank() -> "Amount is required"
                    parsed == null || parsed <= 0 -> "Amount must be a positive number"
                    else -> null
                }
            )
        }
    }

    fun onCategoryChanged(category: ExpenseCategory) {
        _uiState.update { it.copy(category = category) }
    }

    fun onNoteChanged(note: String) {
        _uiState.update { it.copy(note = note) }
    }

    fun saveExpense() {
        val state = _uiState.value
        val titleError = if (state.title.isBlank()) "Title is required" else null
        val amountNum = state.amount.toDoubleOrNull()
        val amountError = when {
            state.amount.isBlank() -> "Amount is required"
            amountNum == null || amountNum <= 0 -> "Amount must be a positive number"
            else -> null
        }

        if (titleError != null || amountError != null || amountNum == null) {
            _uiState.update {
                it.copy(
                    titleError = titleError,
                    amountError = amountError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                addExpenseUseCase(
                    Expense(
                        id = state.id,
                        title = state.title.trim(),
                        amount = amountNum,
                        category = state.category,
                        note = state.note.trim(),
                        date = state.date
                    )
                )
                _uiState.update { it.copy(isSaved = true, isLoading = false) }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
