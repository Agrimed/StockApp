package com.example.stockapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.dao.toStock
import com.example.stockapp.data.model.Stock
import com.example.stockapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CompaniesViewModel(val repository: StockRepository): ViewModel() {

    private val _uiState = MutableStateFlow<List<Stock>>(listOf())
    val uiState: StateFlow<List<Stock>> = _uiState.asStateFlow()

    init {
        getAllStocks()
    }

    fun getAllStocks() {
        viewModelScope.launch {
            _uiState.value = repository.getAllStocks()
                .map { stocksEntity -> stocksEntity.toStock() }
        }
    }
}