package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.viewmodels.ProductDetailsViewModel

private const val productDetailsRoute = "productDetails"
private const val producIdArgument = "productId"

fun NavGraphBuilder.productDetailsScreen(
    onNavigateToCheckout: () -> Unit,
    onPopBackStack: () -> Unit
) {
    composable("${productDetailsRoute}/{$producIdArgument}") { backStackEntry ->
        backStackEntry.arguments?.getString(producIdArgument)?.let { id ->
            val viewModel = viewModel<ProductDetailsViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(Unit) {
                //Mock para exibir a tela de falha (retirar as aspas do id)
                viewModel.findProductById("id")
            }
            ProductDetailsScreen(
                uiState = uiState,
                onOrderClick = onNavigateToCheckout,
                onTryFindProductAgainClick = {
                    viewModel.findProductById(id)
                },
                onBackStack = onPopBackStack)
        } ?: LaunchedEffect(Unit) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToProductDetails(id: String) {
    navigate("$productDetailsRoute/$id")
}