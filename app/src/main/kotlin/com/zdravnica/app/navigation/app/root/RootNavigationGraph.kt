package com.zdravnica.app.navigation.app.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zdravnica.app.navigation.app.navgraphs.AppNavGraph
import com.zdravnica.app.screens.connecting_page.ConnectingPageScreen
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navHostController,
        route = AppNavGraph.Root.route,
        startDestination = startDestination,
        builder = {
            composable(AppNavGraph.Connection.route) {
                ConnectingPageScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ZdravnicaAppTheme.colors.primaryBackgroundColor)
                )
            }
        }
    )
}
