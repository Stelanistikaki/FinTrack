package com.example.expensetracker.ui.navigation

sealed class Screen(val route: String) {
    data object ExpenseList : Screen("expense_list")
    data object AddEditExpense : Screen("add_edit_expense?expenseId={expenseId}") {
        fun createRoute(expenseId: Long = -1L): String {
            return "add_edit_expense?expenseId=$expenseId"
        }
    }
    data object Statistics : Screen("statistics")
}
