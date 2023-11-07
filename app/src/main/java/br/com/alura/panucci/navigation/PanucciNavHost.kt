package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun PanucciNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        homeGraph(
            onNavigateToCheckout = {
                navController.navigateToCheckoutRoute()
            },
            onNavigateToProductDetails = { product ->
                navController.navigateToProductDetails(product.id)
            },
        )
        productDetailsScreen(
            onNavigateToCheckout = {
                navController.navigateToCheckoutRoute()
            },
            onPopBackStack = {
                navController.navigateUp()
            },
        )
        checkoutScreen(
            onPopBackStack = {
                navController.currentBackStackEntry?.savedStateHandle
                    ?.set("order-done", "Pedido realizado com sucesso")
                navController.navigateUp()
            },
        )
    }
}