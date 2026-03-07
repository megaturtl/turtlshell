package cc.turtl.turtlshell.api

fun interface EventListener<T> {
    fun invoke(event: T)
}

class ObservableEvent<T> {
    private val listeners = mutableListOf<EventListener<T>>()

    fun subscribe(listener: EventListener<T>) {
        listeners.add(listener)
    }

    fun subscribe(listener: (T) -> Unit) {
        listeners.add(EventListener { listener(it) })
    }

    fun emit(event: T) {
        listeners.forEach { it.invoke(event) }
    }
}

class CancellableEvent<T> {
    private val listeners = mutableListOf<(T) -> Boolean>()

    fun subscribe(listener: (T) -> Boolean) {
        listeners.add(listener)
    }

    /**
     * Returns false if any listener cancelled the event.
     */
    fun emit(event: T): Boolean {
        return listeners.none { it(event) }
    }
}