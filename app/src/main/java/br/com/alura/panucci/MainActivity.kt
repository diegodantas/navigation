package br.com.alura.panucci

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.panucci.navigation.PanucciNavHost
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightsListRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.navigation.navigateSingleToWithPopTo
import br.com.alura.panucci.navigation.navigateToCheckoutRoute
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.components.bottomAppBarItems
import br.com.alura.panucci.ui.screens.*
import br.com.alura.panucci.ui.theme.PanucciTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val backStackEntryState by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntryState?.destination
            LaunchedEffect(Unit) {
                navController.addOnDestinationChangedListener { _, _, _, ->
                    val route = navController.backQueue.map {
                        it.destination.route
                    }
                    Log.d("System", "$route")
                }
            }
            PanucciTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentRoute = currentDestination?.route
                    val selectedItem by remember(currentDestination) {
                        val item = when(currentRoute) {
                            highlightsListRoute -> BottomAppBarItem.HighlightList
                                menuRoute -> BottomAppBarItem.Menu
                                drinksRoute -> BottomAppBarItem.Drinks
                            else -> BottomAppBarItem.HighlightList
                        }
                        mutableStateOf(item)
                    }
                    val containsInBottomAppBarItems = when(currentRoute) {
                        highlightsListRoute, menuRoute, drinksRoute -> true
                        else -> false
                    }
                    val isShowFab = when (currentDestination?.route) {
                        menuRoute, drinksRoute -> true
                        else -> false
                    }
                    PanucciApp(
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = { item ->
                            navController.navigateSingleToWithPopTo(item)
                        },
                        onFabClick = {
                            navController.navigateToCheckoutRoute()
                        },
                        isShowTopBar = containsInBottomAppBarItems,
                        isShowBottomBar = containsInBottomAppBarItems,
                        isShowFab = isShowFab
                    ) {
                        PanucciNavHost(navController = navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    isShowTopBar: Boolean = false,
    isShowBottomBar: Boolean = false,
    isShowFab: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            if (isShowTopBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Panucci")
                    },
                )
            }
        },
        bottomBar = {
            if (isShowBottomBar) {
                PanucciBottomAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                )
            }
        },
        floatingActionButton = {
            if (isShowFab) {
                FloatingActionButton(
                    onClick = onFabClick
                ) {
                    Icon(
                        Icons.Filled.PointOfSale,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PanucciAppPreview() {
    PanucciTheme {
        Surface {
            PanucciApp {}
        }
    }
}