package com.benoitthore.base.mvvm

import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<S, E> : ViewModel() {

    private var state = MutableLiveData<S>()
    private var event = MutableLiveData<Accumulator<E>>()

    protected abstract val initialState: S

    protected open val dispatchers = Dispatchers

    fun observe(
            lifecycleOwner: LifecycleOwner,
            stateObserver: (S) -> Unit,
            eventObserver: (Accumulator<E>) -> Unit
    ) {
        ensureState()
        state.observe(lifecycleOwner, Observer { stateObserver(it) })
        event.observe(lifecycleOwner, Observer { eventObserver(it) })
    }

    private fun ensureState() {
        if (state.value == null) {
            state.value = initialState
        }
    }

    protected infix fun emitState(func: (S) -> S) {
        checkAndDoOnMain { state.value = func(state.value ?: initialState) }
    }

    protected infix fun emitEvent(event: () -> E) {
        checkAndDoOnMain { this.event.value = (this.event.value ?: Accumulator()) + event() }
    }

    private fun checkAndDoOnMain(block: () -> Unit) {
        if (Looper.getMainLooper() === Looper.myLooper()) {
            block()
        } else {
            viewModelScope.launch(context = dispatchers.Main, block = { block() })
        }
    }
}

class Consumable<T>(private val value: T) {
    private var handled: AtomicBoolean = AtomicBoolean(false)
    /**
     * This function will give you the event value once, then the event becomes used
     * and further calls to this function will not execute anything
     */
    fun consume(block: (T) -> Unit) {
        if (handled.compareAndSet(false, true)) {
            block(value)
        }
    }
}

class Accumulator<T>(private val values: List<T>) {
    constructor() : this(listOf())
    constructor(value: T) : this(listOf(value))

    private var handled = false
    /**
     * This function will call the provided block with the event values stored in
     * this accumulator one by one, and each value will only be given once and only once.
     *
     * Then the events become used and further calls to this function will not execute anything
     */
    fun consume(block: (T) -> Unit) {
        synchronized(this) {
            if (!handled) {
                handled = true
                values.forEach { block(it) }
            }
        }
    }

    operator fun plus(otherValue: T): Accumulator<T> {
        synchronized(this) {
            return if (!handled) {
                handled = true
                Accumulator(values + otherValue)
            } else {
                Accumulator(otherValue)
            }
        }
    }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel()
        = lazy { ViewModelProviders.of(this)[T::class.java] }