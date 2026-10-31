package com.example.expensetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.ui.add_edit.AddEditExpenseScreen
import com.example.expensetracker.ui.add_edit.AddEditExpenseViewModel
import com.example.expensetracker.ui.expenses.ExpensesScreen
import com.example.expensetracker.ui.expenses.ExpensesViewModel
import com.example.expensetracker.ui.statistics.StatisticsScreen
import com.example.expensetracker.ui.statistics.StatisticsViewModel

@Composable
fun ExpenseTrackerNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ExpenseList.route
    ) {
        composable(route = Screen.ExpenseList.route) {
            val viewModel: ExpensesViewModel = hiltViewModel()
            ExpensesScreen(
                viewModel = viewModel,
                onAddExpenseClick = {
                    navController.navigate(Screen.AddEditExpense.createRoute())
                },
                onExpenseClick = { expenseId ->
                    navController.navigate(Screen.AddEditExpense.createRoute(expenseId))
                },
                onNavigateToStatistics = {
                    navController.navigate(Screen.Statistics.route)
                }
            )
        }

        composable(
            route = Screen.AddEditExpense.route,
            arguments = listOf(
                navArgument("expenseId") {
                    type = NavType.StringType
                    defaultValue = "-1"
                    nullable = true
                }
            )
        ) {
            val viewModel: AddEditExpenseViewModel = hiltViewModel()
            AddEditExpenseScreen(
                viewModel = viewModel,
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Statistics.route) {
            val viewModel: StatisticsViewModel = hiltViewModel()
            StatisticsScreen(
                viewModel = viewModel,
                onNavigateToExpenses = {
                    navController.navigate(Screen.ExpenseList.route) {
                        popUpTo(Screen.ExpenseList.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
