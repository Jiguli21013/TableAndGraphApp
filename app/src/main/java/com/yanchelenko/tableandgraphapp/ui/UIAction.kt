package com.yanchelenko.tableandgraphapp.ui

import com.yanchelenko.tableandgraphapp.ui.models.ListOfPointsUI

sealed class UIAction {
    data class SEND(val count: Int) : UIAction()
    //todo с points колхоз, но подругому не придумал как в Нав аргументы закидывать
    data class NAVIGATE(val route: Int, val points: ListOfPointsUI) : UIAction()
}
