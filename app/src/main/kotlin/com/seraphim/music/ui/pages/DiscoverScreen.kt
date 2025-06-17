package com.seraphim.music.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>()
@Composable
fun DiscoverScreen() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Greeting("DiscoverScreen")
    }
}