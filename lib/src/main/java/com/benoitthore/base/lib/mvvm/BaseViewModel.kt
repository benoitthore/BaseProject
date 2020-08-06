package com.benoitthore.base.lib.mvvm

import androidx.lifecycle.*
import com.benoitthore.base.lib.MyDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<S, E> : ViewModel() {

    private var _state = MutableLiveData<S>()
    val state: LiveData<S> get() = _state

    private var _event = MutableLiveData<Accumulator<E>>()
    val lastEvent get() = _event.value?.lastValue

    protected abstract val initialState: S

    fun observe(lifecycleOwner: LifecycleOwner, block: ObserveBuilder.() -> Unit) {
        ObserveBuilder(lifecycleOwner).apply(block).setup()
    }

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
            _event.observe(lifecycleOwner, Observer { eventObserver(it) })
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
        checkAndDoOnMain { this._event.value = (this._event.value ?: Accumulator()) + event() }
    }

    private inline fun checkAndDoOnMain(crossinline block: () -> Unit) {
        viewModelScope.launch(context = Dispatchers.Main, block = { block() })
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
    constructor() : this(emptyList<T>())
    constructor(value: T) : this(listOf(value))

    val lastValue: T? get() = values.lastOrNull()


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