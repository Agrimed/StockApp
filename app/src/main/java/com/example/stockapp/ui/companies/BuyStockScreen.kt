package com.example.stockapp.ui.companies

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockapp.data.dao.StocksEntity
import com.example.stockapp.data.model.Stock
import com.example.stockapp.ui.viewmodel.AppViewModelProvider
import com.example.stockapp.ui.viewmodel.BuyStockViewModel
import java.lang.NumberFormatException


@Composable
fun BuyStockScreen(navBack: () -> Unit) {

    val viewModel: BuyStockViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val stock by viewModel.uiState.collectAsState()

    if (stock != null) {

        var amount = rememberSaveable {
           mutableStateOf(stock!!.amount)
        }

        Column {

            Text(text = stock!!.name)
            Text(text = stock!!.secId)
            Text(text = stock!!.curPrice.toString())

            val initialSum = stock!!.amount * stock!!.curPrice

            Text(text = "Initial sum: $initialSum")
            Text(
                text =
                if (amount.value * stock!!.curPrice - initialSum > 0) "You'll pay: ${amount.value * stock!!.curPrice - initialSum}"
                else "You'll gain: ${initialSum - amount.value * stock!!.curPrice}"
            )


            OutlinedIntegerInput(
                amount =  amount,
                price = stock!!.curPrice,
                onDone = {
                    val updatedStock = StocksEntity(
                        secId = stock!!.secId,
                        name = stock!!.name,
                        curPrice = stock!!.curPrice,
                        amount = amount.value
                    )
                    viewModel.buyStock(updatedStock)
                    navBack()
                }
            )

            Button(
                onClick = {
                    val updatedStock = StocksEntity(
                        secId = stock!!.secId,
                        name = stock!!.name,
                        curPrice = stock!!.curPrice,
                        amount = amount.value
                    )
                    viewModel.buyStock(updatedStock)
                    navBack()
                }

            ) {
                Text(text = "Buy/Sell")
            }

        }
    }
}

@Composable
fun ErrorToast() {
    Toast.makeText(
        LocalContext.current,
        "Error: Can't add data to tablet",
        Toast.LENGTH_LONG
    ).show()
}

@Composable
fun OutlinedIntegerInput(amount: MutableState<Int>, price: Double, onDone: (Int) -> Unit) {

    Column {
        Row{

            IconButton(onClick = { if (amount.value > 0) amount.value-- }) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }

            OutlinedTextField(
                value = if (amount.value == 0) "" else amount.value.toString(),
                onValueChange = { amount.value = try { it.toInt() } catch (e: Exception) { 0 } },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onDone(amount.value) }
                ),
                maxLines = 1
            )

            IconButton(onClick = { amount.value++ }) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
        }
    }
}