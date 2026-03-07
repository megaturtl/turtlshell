package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.api.Event

object TurtlShellClientEvents {
    /**
     * Fired after the client tick.
     */
    val TICK_POST = Event<Unit>()
}