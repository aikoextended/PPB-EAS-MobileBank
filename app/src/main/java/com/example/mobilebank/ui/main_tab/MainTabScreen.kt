package com.example.mobilebank.ui.main_tab

import com.example.mobilebank.ui.home.HomeScreen
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainTabScreen(modifier: Modifier = Modifier, context: Context) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffB5CFB7))
        ) {
            Column(modifier = modifier) {
                HomeScreen(modifier = modifier.padding(top = 100.dp), context = context)
            }
        }
    }
}