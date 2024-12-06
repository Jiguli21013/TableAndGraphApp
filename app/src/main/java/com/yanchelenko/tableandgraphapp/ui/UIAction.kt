package com.yanchelenko.tableandgraphapp.ui

sealed class UIAction {
    data class SEND(val count: Int) : UIAction()
    data class NAVIGATE(val route: Int) : UIAction()
}
