package com.example.stockapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.dao.StocksEntity
import com.example.stockapp.data.dao.toStock
import com.example.stockapp.data.model.Stock
import com.example.stockapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyStocksViewModel(val repository: StockRepository): ViewModel() {
    //flow == coroutine
    val uiState: StateFlow<List<Stock>> =
        repository.getAllMyStocks()
            .map { stockEntityList ->
                stockEntityList
                    .map { stocksEntity -> stocksEntity.toStock() }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = listOf()
            )
}