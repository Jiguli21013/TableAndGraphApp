package com.yanchelenko.tableandgraphapp.utils.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.observeWithLifecycle(owner: LifecycleOwner, consumer: (T) -> Unit) {
    owner.lifecycleScope.launch {
        flowWithLifecycle(owner.lifecycle, Lifecycle.State.STARTED).collect {
            consumer(it)
        }
    }
}
