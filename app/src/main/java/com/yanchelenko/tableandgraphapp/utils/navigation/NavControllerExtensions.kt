package com.yanchelenko.tableandgraphapp.utils.navigation

import androidx.navigation.NavController

fun NavController.setCurrentScreenWithNavController(screen: SCREENS) {
    if (screen.navDirections == null) {
        runCatching { navigate(screen.screenId) }
    } else {
        runCatching {
            navigate(screen.navDirections ?: return)
            screen.navDirections = null
        }
    }
}
