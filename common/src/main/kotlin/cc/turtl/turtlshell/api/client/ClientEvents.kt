package cc.turtl.turtlshell.api.client

import cc.turtl.turtlshell.impl.CancellableEvent
import cc.turtl.turtlshell.impl.ObservableEvent
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity

object ClientEvents {
    /**
     * Fired after the client tick.
     */
    val TICK_POST = ObservableEvent<Unit>()

    val ENTITY_LOAD = ObservableEvent<Entity>()
    val ENTITY_UNLOAD = ObservableEvent<Entity>()

    val LEVEL_CONNECTED = ObservableEvent<Unit>()
    val LEVEL_DISCONNECTED = ObservableEvent<Unit>()

    val GAME_STOPPING = ObservableEvent<Unit>()

    val COMMAND_SENT = ObservableEvent<String>()

    val MESSAGE_RECEIVED = CancellableEvent<Component>()
}