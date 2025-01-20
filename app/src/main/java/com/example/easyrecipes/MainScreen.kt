package com.example.easyrecipes

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreen(navController: NavHostController) {
    var recipes by remember { mutableStateOf<List<RecipeDto>>(emptyList()) }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    val callRandomService = apiService.getRandomRecipes()

    callRandomService.enqueue(object : Callback<RecipesResponse> {
        override fun onResponse(
            call: Call<RecipesResponse?>,
            response: Response<RecipesResponse?>
        ) {
            if (response.isSuccessful) {
                recipes = response.body()?.recipes ?: emptyList()

            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<RecipesResponse?>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }

    })

    MainContent(
        recipes = recipes,
    ) { itemClicked ->
        navController.navigate(route = "recipeDetail/${itemClicked.id}")
    }
}

@Composable
private fun MainContent(
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit,
) {
    RecipesSession(
        label = "Recipes",
        recipes = recipes,
        onClick = onClick
    )
}

@Composable
private fun RecipesSession(
    label: String,
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = label
        )

        RecipeList(
            recipes = recipes,
            onClick = onClick
        )
    }
}

@Composable
private fun RecipeList(
    modifier: Modifier = Modifier,
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(recipes) {
            RecipeItem(recipe = it, onClick = onClick)
        }
    }
}

@Composable
private fun RecipeItem(
    recipe: RecipeDto,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick.invoke(recipe)
            }
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            model = recipe.image, contentDescription = "${recipe.title} Image"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            text = recipe.title
        )

    }
}