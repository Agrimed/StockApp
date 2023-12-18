package com.example.stockapp.ui.companies

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockapp.R
import com.example.stockapp.data.dao.StocksEntity
import com.example.stockapp.data.model.Stock
import com.example.stockapp.ui.composable.TopBar
import com.example.stockapp.ui.viewmodel.AppViewModelProvider
import com.example.stockapp.ui.viewmodel.BuyStockViewModel
import java.lang.NumberFormatException


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BuyStockScreen(navBack: () -> Unit) {

    val viewModel: BuyStockViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val stock by viewModel.uiState.collectAsState()

    val amount = remember { mutableStateOf(stock.amount) }

    val keyBoardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.companies),
                navBack = navBack
            )
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Text(text = stock.secId, fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stock.name, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(80.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Stock Price: ".format(stock.curPrice),
                    fontSize = 24.sp,
                )
                Text(
                    text = "₽%.2f".format(stock.curPrice),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            val initialSum = stock.amount * stock.curPrice

//            OutlinedIntegerInput(
//                amount = amount,
//                price = stock.curPrice,
//                onDone = {
////                    val updatedStock = StocksEntity(
////                        secId = stock.secId,
////                        name = stock.name,
////                        curPrice = stock.curPrice,
////                        amount = amount.value
////                    )
////                    viewModel.buyStock(updatedStock)
////                    navBack()
//                }
//            )

//----------------------------------------------------------------------------------------------------------
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
                            onDone = { keyBoardController?.hide() }
                        ),
                        maxLines = 1,
                        placeholder = { Text(text = "Enter stock amount") }
                    )

                    IconButton(onClick = { amount.value++ }) {
                        Icon(Icons.Default.KeyboardArrowUp, null)
                    }
                }
            }
//-----------------------------------------------------------------------------------------------------

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier
                .fillMaxWidth()) {
                Text(
                    text = "Initial sum: ",
                    fontSize = 20.sp
                )

                Text(
                    text = "₽%.2f".format(initialSum),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()) {

                val transactionPrice = amount.value * stock.curPrice - initialSum



                if ( transactionPrice > 0) {

                    Text(
                        text = "Purchase Price: ",
                        fontSize = 20.sp,
                    )

                    Text(
                        text = "₽%.2f".format(transactionPrice),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )


                } else {

                    Text(
                        text = "Sale Price: ",
                        fontSize = 20.sp,
                    )

                    Text(
                        text = "₽%.2f".format(-transactionPrice),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    val updatedStock = StocksEntity(
                        secId = stock.secId,
                        name = stock.name,
                        curPrice = stock.curPrice,
                        amount = amount.value
                    )
                    viewModel.buyStock(updatedStock)
                    navBack()
                }

            ) {
                Text(text = "Buy/Sell", fontSize = 24.sp)
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedIntegerInput(
    amount: MutableState<Int>,
    price: Double, onDone: (Int) -> Unit) {


}