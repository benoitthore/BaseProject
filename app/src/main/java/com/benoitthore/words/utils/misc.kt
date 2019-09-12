package com.benoitthore.words.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface CoroutineAware {
    val scope: CoroutineScope
    val mainThreadDispatcher: CoroutineDispatcher

    fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) {
        scope.launch(mainThreadDispatcher, block = block)
    }
}

