package com.example.mobilebank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.example.mobilebank.ui.main_tab.MainTabScreen
import com.example.mobilebank.ui.theme.GreenBankApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenBankApplicationTheme {
                MainTabScreen(modifier = Modifier, context = this)
            }
        }
    }
}

