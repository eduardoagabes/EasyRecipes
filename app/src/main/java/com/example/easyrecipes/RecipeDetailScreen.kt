package com.example.easyrecipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RecipeDetailScreen() {
    RecipeDetailContent()
}

@Composable
private fun RecipeDetailContent() {
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "Recipes Detail Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreview() {
    RecipeDetailContent()

}

