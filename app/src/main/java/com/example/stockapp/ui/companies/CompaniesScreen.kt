package com.example.stockapp.ui.companies

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockapp.ui.composable.TopBar
import com.example.stockapp.R
import com.example.stockapp.data.model.Stock
import com.example.stockapp.ui.viewmodel.AppViewModelProvider
import com.example.stockapp.ui.viewmodel.CompaniesViewModel

@Composable
fun CompaniesScreen(
    stockList: List<Stock>,
    navBack: () -> Unit,
    stockCardClick: (String) -> Unit,
) {

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.companies),
                navBack = navBack
            )
    }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ){
            items(stockList) { stock ->
                StockCard(stock, stockCardClick = stockCardClick)
            }
        }
    }

}

@Composable
fun StockCard(stock: Stock, stockCardClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .clickable { stockCardClick(stock.secId) }
    ) {
        Row() {
            Text(text = stock.secId)
            Text(text = stock.name)
            Text(text = stock.curPrice.toString())
        }
    }
}
