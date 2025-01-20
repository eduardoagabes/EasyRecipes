package com.example.easyrecipes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun EasyRecipesApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboardingScreen") {
        composable(route = "onboardingScreen") {
            OnboardingScreen(navController)
        }
        composable(route = "RecipesList") {
            MainScreen(navController)
        }
        composable(
            route = "recipeDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })
        ) { backStrackEntry ->
            val recipeId = requireNotNull(backStrackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(recipeId, navController)
        }
    }
}
