package com.seraphim.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        window.edgeToEdgeWindowInsetsControllerCompat()
//        setContent {
//            MusicTheme {
//                MainScreen()
//            }
//        }
    }
}