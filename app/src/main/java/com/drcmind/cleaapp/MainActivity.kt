package com.drcmind.cleaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.drcmind.cleaapp.ui.navigation.AppNavigation
import com.drcmind.cleaapp.ui.theme.CleaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleaAppTheme {
                    AppNavigation()

            }
        }
    }
}
