package cc.turtl.turtlshell.api

fun interface EventListener<T> {
    fun invoke(event: T)
}

class Event<T> {
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