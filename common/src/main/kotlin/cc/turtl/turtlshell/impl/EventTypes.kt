package cc.turtl.turtlshell.impl

/**
 * Emits a value to all subscribers.
 *
 * ```kotlin
 * val onTick = ObservableEvent<Unit>()
 *
 * onTick.subscribe { doSomething() }
 *
 * onTick(Unit)
 * ```
 *
 * [subscribe] returns an unsubscribe lambda:
 * ```kotlin
 * val unsub = onTick.subscribe { doSomething() }
 * unsub()
 * ```
 */
class ObservableEvent<T> {
    private val listeners = mutableListOf<(T) -> Unit>()

    fun subscribe(listener: (T) -> Unit): () -> Unit {
        listeners.add(listener)
        return { listeners.remove(listener) }
    }

    operator fun invoke(event: T) {
        listeners.forEach { it(event) }
    }
}

/**
 * Emits a value to subscribers until one cancels it.
 * Listeners return true to cancel, false to continue.
 * [invoke] returns true if the event was cancelled.
 *
 * ```kotlin
 * val onMessage = CancellableEvent<String>()
 *
 * onMessage.subscribe { message -> message.startsWith("!") }
 *
 * if (onMessage("!hello")) return // cancelled
 * ```
 */
class CancellableEvent<T> {
    private val listeners = mutableListOf<(T) -> Boolean>()

    fun subscribe(listener: (T) -> Boolean): () -> Unit {
        listeners.add(listener)
        return { listeners.remove(listener) }
    }

    /** Returns true if the event was cancelled. */
    operator fun invoke(event: T): Boolean =
        listeners.any { it(event) }
}