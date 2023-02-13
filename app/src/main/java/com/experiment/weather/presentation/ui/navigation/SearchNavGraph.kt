package com.experiment.weather.presentation.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.navigation
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.experiment.weather.presentation.ui.ManageLocations
import com.weather.feature.search.SearchScreen
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalAnimationApi
fun NavGraphBuilder.searchNavGraph(navController: NavController) {
    navigation(
        startDestination = Graph.Search.ManageLocationScreen,
        route = Graph.Search.graph
    ) {
        composable(
            route = Graph.Search.ManageLocationScreen,
            enterTransition = {
                when (initialState.destination.route) {
                    Graph.Search.SearchScreen -> fadeIn(tween(500))
                    else -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right,tween(500))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Graph.Search.SearchScreen -> fadeOut(tween(500))
                    else -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,tween(500))
                }
            }
        ) {
            ManageLocations(
                onNavigateToSearch = {
                    navController.navigate(Graph.Search.SearchScreen) {

                    }
                },
                onBackPressed = {
                    navController.navigate(Graph.Forecast.graph) {
                        popUpTo(Graph.Search.ManageLocationScreen) { inclusive = true }
                    }
                },
                onItemSelected = { cityName ->
                    navController.navigate(Graph.Forecast.passForecastArgument(cityName = cityName)) {
                        popUpTo(Graph.Forecast.graph) { inclusive = true }
                    }
                })
        }
        composable(
            route = Graph.Search.SearchScreen,
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            SearchScreen(
                onSelectSearchItem = {
                    navController.navigate(Graph.Search.ManageLocationScreen) {
                        launchSingleTop = true
                        popUpTo(Graph.Search.ManageLocationScreen)
                    }
                }
            )
        }
    }
}