package com.example.stockapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.dao.StocksEntity
import com.example.stockapp.data.dao.toStock
import com.example.stockapp.data.model.Stock
import com.example.stockapp.data.repository.StockRepository
import com.example.stockapp.ui.navigation.BuyStockScreenDest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BuyStockViewModel(
    val savedStateHandle: SavedStateHandle, //created by Android and contain something
    val repository: StockRepository
): ViewModel() {

    private val secId: String = checkNotNull(savedStateHandle[BuyStockScreenDest.stockIdArg])

    val uiState: StateFlow<Stock> =
        repository.getStockById(secId).map { it.toStock() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = Stock("", "", 0.0, 0)
        )

    fun buyStock(item: StocksEntity) {
        viewModelScope.launch {//start coroutine
           repository.update(item)
        }
    }

    companion object { //companion like static in java
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

