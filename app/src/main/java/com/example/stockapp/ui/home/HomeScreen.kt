package com.example.stockapp.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockapp.R

@Composable
fun HomeScreen(onCompaniesClick: () -> Unit, onStockClick: () -> Unit) {
    // Orientation ------------------
    val config = LocalConfiguration.current
    val orientationMode by remember { mutableStateOf(config.orientation) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color =MaterialTheme.colorScheme.background
    ) {
        if (orientationMode == Configuration.ORIENTATION_PORTRAIT) {
            PortraitHomeScreen(
                onStockClick = onStockClick,
                onCompaniesClick = onCompaniesClick
            )
        }
        else {
            LandscapeHomeScreen(
                onStockClick = onStockClick,
                onCompaniesClick = onCompaniesClick
            )
        }
    }
}

@Composable
fun PortraitHomeScreen(
    onCompaniesClick: () -> Unit,
    onStockClick: () -> Unit,
) {
    Column(

    ) {
        HomeScreenImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                //.border(2.dp, Color.Green)
                .weight(2f)
        )
        HomeScreenButtons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onStockClick = onStockClick,
            onCompaniesClick = onCompaniesClick
        )
    }
}

@Composable
fun LandscapeHomeScreen(
    onCompaniesClick: () -> Unit,
    onStockClick: () -> Unit,
) {
    Row {
        HomeScreenImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(400.dp)
                //.border(2.dp, Color.Green)
                .weight(2f)
        )
        HomeScreenButtons(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            onStockClick = onStockClick,
            onCompaniesClick = onCompaniesClick
        )
    }
}

@Composable
fun HomeScreenImage(modifier: Modifier = Modifier) {

    val image = painterResource(id = R.drawable.logo)

    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = image, contentDescription = null)
    }
}

@Composable
fun HomeScreenButtons(
    modifier: Modifier = Modifier,
    onStockClick: () -> Unit,
    onCompaniesClick: () -> Unit,
) {
    val buttonModifier = Modifier
        .fillMaxWidth()

    Column (
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {

        val textSize = 24.sp

        Button(
            onClick = onCompaniesClick,
            modifier = buttonModifier
        ) {
            Text(
                text = stringResource(R.string.companies),
                fontSize = textSize
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(
            onClick = onStockClick,
            modifier = buttonModifier
        ) {
            Text(
                text = stringResource(R.string.stocks),
                fontSize = textSize
            )
        }
    }
}