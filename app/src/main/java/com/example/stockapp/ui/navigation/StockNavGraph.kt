package com.example.stockapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockapp.ui.companies.BuyStockScreen
import com.example.stockapp.ui.companies.CompaniesScreen
import com.example.stockapp.ui.home.HomeScreen
import com.example.stockapp.ui.viewmodel.AppViewModelProvider
import com.example.stockapp.ui.viewmodel.CompaniesViewModel
import com.example.stockapp.ui.viewmodel.MyStocksViewModel

@Composable
fun StockNavGraph(navController: NavHostController = rememberNavController()) {

    val viewModel: CompaniesViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val stockList by viewModel.uiState.collectAsState()

    val myStockViewModel: MyStocksViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val myStockList by myStockViewModel.uiState.collectAsState()

    NavHost (
        navController = navController,
        startDestination = HomeScreenDest.route,
    ) {

        composable(route = HomeScreenDest.route) {
            HomeScreen(
                onStockClick = { navController.navigate(route = MyStockScreenDest.route) },
                onCompaniesClick = { navController.navigate(route = CompaniesScreenDest.route) }
            )
        }

        composable(route = CompaniesScreenDest.route) {
            CompaniesScreen(
                navBack = { navController.popBackStack() },
                stockCardClick = { navController.navigate(route = "${BuyStockScreenDest.route}/$it") },
                stockList = stockList
            )
        }

        composable(
            route = BuyStockScreenDest.routeWithArgs,
            arguments = listOf(navArgument(BuyStockScreenDest.stockIdArg) {type = NavType.StringType}) //id of stock card
        ) {
            BuyStockScreen (
                navBack = { navController.popBackStack() }
            )
        }

        composable(route = MyStockScreenDest.route) {
            CompaniesScreen(
                navBack = { navController.popBackStack() },
                stockCardClick = { navController.navigate(route = "${BuyStockScreenDest.route}/$it") },
                stockList = myStockList
            )
        }

    }
}