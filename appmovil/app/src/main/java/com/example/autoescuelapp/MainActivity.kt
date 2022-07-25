package com.example.autoescuelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.autoescuelapp.ui.common.Navigation
import com.example.autoescuelapp.ui.theme.AutoescuelAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoescuelAppTheme {
                Navigation()
            }
        }
    }
}