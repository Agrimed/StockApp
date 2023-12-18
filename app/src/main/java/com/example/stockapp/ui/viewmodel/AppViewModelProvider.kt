package com.example.stockapp.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stockapp.StockApp

object AppViewModelProvider {

    val Factory: ViewModelProvider.Factory = viewModelFactory {

        initializer {
            CompaniesViewModel(
                repository = stockApplication().container.stockRepository
            )
        }

        initializer {
            BuyStockViewModel(
                savedStateHandle = this.createSavedStateHandle(), // get id from navigation
                repository = stockApplication().container.stockRepository
            )
        }

        initializer {
            MyStocksViewModel(
                repository = stockApplication().container.stockRepository
            )
        }

    }
}

fun CreationExtras.stockApplication(): StockApp =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as StockApp) //StockApp