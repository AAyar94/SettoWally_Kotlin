package com.settowally.settowally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.settowally.settowally.core.theme.SettoWallyTheme
import com.settowally.settowally.presentation.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettoWallyTheme {
                HomeScreen()
            }
        }
    }
}