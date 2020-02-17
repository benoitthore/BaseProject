package com.benoitthore.base.lib

import android.view.View
import com.benoitthore.backingfield.BackingField
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


val View.viewScope: CoroutineScope by BackingField {

    val scope = ReusableCoroutineScope(SupervisorJob() + Dispatchers.Main)

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

internal class ReusableContextScope(
        context: CoroutineContext,
        private val newJob: () -> Job
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

/**
 * Creates a scope that can be cancelled and then restarted
 */
@Suppress("FunctionName")
fun ReusableCoroutineScope(
        context: CoroutineContext,
        newJob: () -> Job = { SupervisorJob() }
): CoroutineScope =
        ReusableContextScope(context, newJob)
