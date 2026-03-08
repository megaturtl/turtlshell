package cc.turtl.turtlshell.client

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents

object EventBridgeClientFabric {
    fun register() {
        ClientTickEvents.END_CLIENT_TICK.register {
            TurtlShellClientEvents.TICK_POST(Unit)
        }

        ClientEntityEvents.ENTITY_LOAD.register { entity, _ ->
            TurtlShellClientEvents.ENTITY_LOAD(entity)
        }

        ClientEntityEvents.ENTITY_UNLOAD.register { entity, _ ->
            TurtlShellClientEvents.ENTITY_UNLOAD(entity)
        }

        ClientPlayConnectionEvents.JOIN.register { _, _, _ ->
            TurtlShellClientEvents.LEVEL_CONNECTED(Unit)
        }

        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            TurtlShellClientEvents.LEVEL_DISCONNECTED(Unit)
        }

        ClientLifecycleEvents.CLIENT_STOPPING.register {
            TurtlShellClientEvents.GAME_STOPPING(Unit)
        }

        ClientSendMessageEvents.COMMAND.register { command ->
            TurtlShellClientEvents.COMMAND_SENT(command)
        }

        ClientReceiveMessageEvents.ALLOW_GAME.register { message, overlay ->
            if (overlay) return@register true
            !TurtlShellClientEvents.MESSAGE_RECEIVED(message)
        }
    }
}