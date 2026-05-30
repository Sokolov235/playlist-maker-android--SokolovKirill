package com.example.myapplication.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myapplication.ui.navigation.Screen
import com.example.myapplication.ui.screen.MainScreen
import com.example.myapplication.ui.screen.SearchScreen
import com.example.myapplication.ui.screen.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.name
    ) {

        composable(Screen.MAIN.name) {
            MainScreen(
                onSearchClick = {
                    navController.navigate(Screen.SEARCH.name)
                },
                onSettingsClick = {
                    navController.navigate(Screen.SETTINGS.name)
                }
            )
        }

        composable(Screen.SEARCH.name) {
            SearchScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}