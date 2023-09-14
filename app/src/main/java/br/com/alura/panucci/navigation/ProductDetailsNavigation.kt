package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen

private const val productDetailsRoute = "productDetails"
private const val producIdArgument = "productId"

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
    composable("${productDetailsRoute}/{$producIdArgument}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString(producIdArgument)
        sampleProducts.find {
            it.id == id
        }?.let {
            ProductDetailsScreen(
                product = it,
                onNavigateToCheckout = {
                    navController.navigateToCheckoutRoute()
                })
        } ?: LaunchedEffect(Unit) {
            navController.navigateUp()
        }
    }
}

fun NavController.navigateToProductDetails(id : String) {
    navigate("$productDetailsRoute/$id")
}