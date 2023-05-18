package com.example.boredapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.boredapp.ui.about.AboutScreen
import com.example.boredapp.ui.details.Details
import com.example.boredapp.ui.main.BoredAppMainScreen

@Composable
fun BoredAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavScreen.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavScreen.Home.route) {
            BoredAppMainScreen(
                onNavigateToAbout = { navController.navigate(NavScreen.About.route) },
                onNavigateToDetails = {navController.navigate("${NavScreen.Details.route}/$it") {popUpTo(NavScreen.Home.route)} },
                viewModel = hiltViewModel()
        )}
        composable(NavScreen.About.route) { AboutScreen(navigateUp = { navController.navigateUp() }) }
        composable(route = NavScreen.Details.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.Details.argument0) { type = NavType.LongType }),
        ) { backStackEntry ->
            val activityKey =
            backStackEntry.arguments?.getLong(NavScreen.Details.argument0) ?: return@composable

            Details(
                activityKey = activityKey,
                navigateUp = { navController.navigateUp() },
                viewModel = hiltViewModel(),
                onNavigateToFavourites = { navController.navigate(NavScreen.Home.route) }
            )
        }
    }
}

sealed class NavScreen(val route: String) {

    object Home : NavScreen("home")

    object About : NavScreen("about")

    object Details : NavScreen("details") {

        const val routeWithArgument: String = "details/{activityKey}"

        const val argument0: String = "activityKey"
    }
}