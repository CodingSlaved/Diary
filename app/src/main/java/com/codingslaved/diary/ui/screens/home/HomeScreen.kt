package com.codingslaved.diary.ui.screens.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.NavController
import com.codingslaved.diary.ui.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column (modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Button(onClick = {navController.navigate(Routes.CALENDAR)}, modifier = Modifier.wrapContentSize()) {
                Text(text = "go to calendar")
            }
        }
    }
}
