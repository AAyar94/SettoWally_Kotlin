package com.settowally.settowally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.settowally.settowally.core.navigation.AppNavigation
import com.settowally.settowally.core.theme.SettoWallyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettoWallyTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colorScheme.primaryContainer)
                systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.primaryContainer)
                AppNavigation()
            }
        }
    }
}