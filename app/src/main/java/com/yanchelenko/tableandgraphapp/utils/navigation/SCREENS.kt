package com.yanchelenko.tableandgraphapp.utils.navigation

import androidx.navigation.NavDirections
import com.yanchelenko.tableandgraphapp.R

enum class SCREENS(
    val screenId: Int,
    var navDirections: NavDirections? = null
) {
    HOME(R.id.homeFragment),
    TABLE(R.id.tableFragment)
}
