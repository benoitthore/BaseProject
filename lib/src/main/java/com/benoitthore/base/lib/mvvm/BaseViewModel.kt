package com.benoitthore.base.lib.mvvm

import android.os.Looper
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<S, E> : ViewModel() {

    private var _state = MutableLiveData<S>()
    val state: LiveData<S> get() = _state

    private var event = MutableLiveData<Accumulator<E>>()

    protected abstract val initialState: S

    protected open val dispatchers = Dispatchers

    fun observe(
            lifecycleOwner: LifecycleOwner,
            stateObserver: ((S) -> Unit)?,
            eventObserver: ((Accumulator<E>) -> Unit)?
    ) {
        ensureState()
        if (stateObserver != null) {
            _state.observe(lifecycleOwner, Observer { stateObserver(it) })
        }
        if (eventObserver != null) {
            event.observe(lifecycleOwner, Observer { eventObserver(it) })
        }
    }

    private fun ensureState() {
        if (_state.value == null) {
            _state.value = initialState
        }
    }

    protected infix fun emitState(func: (S) -> S) {
        checkAndDoOnMain { _state.value = func(_state.value ?: initialState) }
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

    fun observe(lifecycleOwner: LifecycleOwner, block: ObserveBuilder.() -> Unit) {
        ObserveBuilder(lifecycleOwner).apply(block).setup()
    }

    inner class ObserveBuilder internal constructor(val lifecycleOwner: LifecycleOwner) {

        private var stateObserver: ((S) -> Unit)? = null
        private var eventObserver: ((Accumulator<E>) -> Unit)? = null

        fun stateObserver(value: (S) -> Unit) = apply {
            stateObserver = value
        }

        fun eventObserver(value: (Accumulator<E>) -> Unit) = apply {
            eventObserver = value
        }

        fun setup() {
            observe(lifecycleOwner, stateObserver, eventObserver)
        }
    }
}

class Accumulator<T>(private val values: List<T>) {
    constructor() : this(emptyList<T>())
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