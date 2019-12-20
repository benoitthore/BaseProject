package com.benoitthore.base.lib.coroutines

import androidx.lifecycle.viewModelScope
import com.benoitthore.base.lib.mvvm.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit)
        = launch(block = block)

fun BaseViewModel<*, *>.mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit)
        = viewModelScope.launch(block = block)