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
            route = "recipe_detail" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val recipeId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(recipeId, navController)
        }
        composable(
            route = "search_recipes" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController)
        }
    }
}
