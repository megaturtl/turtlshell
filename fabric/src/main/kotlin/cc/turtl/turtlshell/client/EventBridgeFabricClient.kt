package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.api.client.ClientEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents

object EventBridgeFabricClient {
    fun register() {
        ClientTickEvents.END_CLIENT_TICK.register {
            ClientEvents.TICK_POST(Unit)
        }

        ClientEntityEvents.ENTITY_LOAD.register { entity, _ ->
            ClientEvents.ENTITY_LOAD(entity)
        }

        ClientEntityEvents.ENTITY_UNLOAD.register { entity, _ ->
            ClientEvents.ENTITY_UNLOAD(entity)
        }

        ClientPlayConnectionEvents.JOIN.register { _, _, _ ->
            ClientEvents.LEVEL_CONNECTED(Unit)
        }

        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            ClientEvents.LEVEL_DISCONNECTED(Unit)
        }

        ClientLifecycleEvents.CLIENT_STOPPING.register {
            ClientEvents.GAME_STOPPING(Unit)
        }

        ClientSendMessageEvents.COMMAND.register { command ->
            ClientEvents.COMMAND_SENT(command)
        }

        ClientReceiveMessageEvents.ALLOW_GAME.register { message, overlay ->
            if (overlay) return@register true
            !ClientEvents.MESSAGE_RECEIVED(message)
        }
    }
}