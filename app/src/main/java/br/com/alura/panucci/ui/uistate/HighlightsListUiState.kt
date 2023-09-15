package br.com.alura.panucci.ui.uistate

import br.com.alura.panucci.model.Product

data class HighlightsListUiState(
    var products: List<Product> = emptyList()
)