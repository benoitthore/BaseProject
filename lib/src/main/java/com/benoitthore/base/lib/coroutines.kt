package com.benoitthore.base.lib

import android.view.View
import com.benoitthore.backingfield.BackingField
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

data class MyDispatchers(
        val main: CoroutineDispatcher = Dispatchers.Main,
        val io: CoroutineDispatcher = Dispatchers.IO,
        val unconfined: CoroutineDispatcher = Dispatchers.Unconfined,
        val default: CoroutineDispatcher = Dispatchers.Default
)


val View.viewScope: CoroutineScope by BackingField {

    val scope = ReusableContextScope(SupervisorJob() + Dispatchers.Main)

    addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {
                    scope.cancel()
                }

                override fun onViewAttachedToWindow(v: View?) {
                }
            }
    )
    return@BackingField scope
}

class ReusableContextScope(
        context: CoroutineContext,
        private val newJob: () -> Job = { SupervisorJob() }
) : CoroutineScope {
    private var reusableContext: CoroutineContext = context

    override val coroutineContext: CoroutineContext
        get() {
            if (reusableContext[Job]?.isCancelled == true) {
                reusableContext += newJob()
            }
            return reusableContext
        }
}

