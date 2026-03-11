package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.api.client.ClientEvents
import net.minecraft.client.multiplayer.ClientLevel
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientChatEvent
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.event.GameShuttingDownEvent
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent

@EventBusSubscriber
object EventBridgeNeoForgeClient {

    @SubscribeEvent
    fun onClientTick(e: ClientTickEvent.Post) {
        ClientEvents.TICK_POST(Unit)
    }

    @SubscribeEvent
    fun onEntityLoad(e: EntityJoinLevelEvent) {
        if (e.level !is ClientLevel) return
        ClientEvents.ENTITY_LOAD(e.entity)
    }

    @SubscribeEvent
    fun onEntityUnload(e: EntityLeaveLevelEvent) {
        if (e.level !is ClientLevel) return
        ClientEvents.ENTITY_UNLOAD(e.entity)
    }

    @SubscribeEvent
    fun onConnect(e: ClientPlayerNetworkEvent.LoggingIn) {
        ClientEvents.LEVEL_CONNECTED(Unit)
    }

    @SubscribeEvent
    fun onDisconnect(e: ClientPlayerNetworkEvent.LoggingOut) {
        ClientEvents.LEVEL_DISCONNECTED(Unit)
    }

    @SubscribeEvent
    fun onGameStopping(e: GameShuttingDownEvent) {
        ClientEvents.GAME_STOPPING(Unit)
    }

    @SubscribeEvent
    fun onCommandSent(e: ClientChatEvent) {
        if (!e.originalMessage.startsWith("/")) return
        ClientEvents.COMMAND_SENT(e.originalMessage.removePrefix("/"))
    }

    @SubscribeEvent
    fun onMessageReceived(e: ClientChatReceivedEvent.System) {
        if (e.isOverlay) return
        val cancelled = ClientEvents.MESSAGE_RECEIVED(e.message)
        if (cancelled) e.isCanceled = true
    }
}