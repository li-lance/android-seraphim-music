package com.seraphim.music.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.DiscoverScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FavoriteScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import com.seraphim.music.R

@Composable
fun BottomNavBar(navController: NavHostController, navigator: DestinationsNavigator) {
    val screens = BottomNavItem.entries.toList()
//    val navigator = navController.rememberDestinationsNavigator()

//    val navBackStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(containerColor = MaterialTheme.colorScheme.onBackground) {
        screens.forEach { destination ->
            val isCurrentDestOnBackStack by navController.isRouteOnBackStackAsState(destination.direction)

            NavigationBarItem(
                selected = isCurrentDestOnBackStack, onClick = {
                if (isCurrentDestOnBackStack) {
                    // When we click again on a bottom bar item and it was already selected
                    // we want to pop the back stack until the initial destination of this bottom bar item
                    navigator.popBackStack(destination.direction, false)
                    return@NavigationBarItem
                }
                navigator.navigate(destination.direction) {
                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.root) {
                        saveState = true
                    }

                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }, icon = {
                Icon(
                    ImageVector.vectorResource(destination.icon),
                    contentDescription = stringResource(destination.label),
                )
            }, label = {
//                Text(stringResource(destination.label))
            },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = if (isCurrentDestOnBackStack) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        }
    }

}

enum class BottomNavItem(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, R.drawable.ic_home_unselected, R.string.main_tab_home),
    Discover(
        DiscoverScreenDestination,
        R.drawable.ic_discovery_unselected,
        R.string.main_tab_discover
    ),
    Favorite(
        FavoriteScreenDestination,
        R.drawable.ic_favorite_unselected,
        R.string.main_tab_favorite
    ),
    Profile(ProfileScreenDestination, R.drawable.ic_profile_unselected, R.string.main_tab_profile);
}