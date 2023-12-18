package com.example.stockapp.ui.companies

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockapp.ui.composable.TopBar
import com.example.stockapp.R
import com.example.stockapp.data.model.Stock
import com.example.stockapp.ui.theme.StockAPPTheme
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
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(5f)) {
                Text(text = stock.secId, fontWeight = FontWeight.Bold)
                Text(text = stock.name, maxLines = 1)
            }
            Text(
                text = "₽%.2f".format(stock.curPrice),
                modifier = Modifier.weight(2f)
            )
            
            IconButton(
                onClick = { stockCardClick(stock.secId) },
                modifier = Modifier.weight(1f)
            ) {
                Icon( Icons.Default.ShoppingCart, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun StockCardPreview() {
    StockAPPTheme {
        StockCard(stock = Stock("A-RM", "Agilent TechnologiesORD SHS", 10427.0, 0),
            stockCardClick = {})
    }
}