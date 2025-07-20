package com.behnamuix.hogwarts.View.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object MainSc: Screen{
    @Composable
    override fun Content() {
        MainContent()
    }

}
object HogwartsTestSc: Screen{
    @Composable
    override fun Content() {
        HogwartsTestContent()
    }

}
