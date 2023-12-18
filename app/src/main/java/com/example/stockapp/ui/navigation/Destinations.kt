package com.example.stockapp.ui.navigation

import com.example.stockapp.R

interface NavigationDestination {
    val route: String
    val title: Int
}

object HomeScreenDest: NavigationDestination {
    override val route = "home"
    override val title = R.string.app_name
}

object CompaniesScreenDest: NavigationDestination {
    override val route = "companies"
    override val title = R.string.companies
}

object BuyStockScreenDest: NavigationDestination {
    override val route = "companies"
    override val title = R.string.buyStock
    val stockIdArg = "secId"
    val routeWithArgs = "$route/{$stockIdArg}"
}

object MyStockScreenDest: NavigationDestination {
    override val route = "myStock"
    override val title = R.string.my_stocks
}