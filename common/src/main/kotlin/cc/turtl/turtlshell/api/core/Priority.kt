package cc.turtl.turtlshell.api.core

enum class Priority {
    LOWEST, LOW, NORMAL, HIGH, HIGHEST;

    fun isHigherThan(other: Priority): Boolean {
        return this.ordinal > other.ordinal
    }
}