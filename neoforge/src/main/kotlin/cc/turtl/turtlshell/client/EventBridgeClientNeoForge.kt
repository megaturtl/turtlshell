package cc.turtl.turtlshell.client

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
object EventBridgeClientNeoForge {

    @SubscribeEvent
    fun onClientTick(e: ClientTickEvent.Post) {
        TurtlShellClientEvents.TICK_POST(Unit)
    }

    @SubscribeEvent
    fun onEntityLoad(e: EntityJoinLevelEvent) {
        if (e.level !is ClientLevel) return
        TurtlShellClientEvents.ENTITY_LOAD(e.entity)
    }

    @SubscribeEvent
    fun onEntityUnload(e: EntityLeaveLevelEvent) {
        if (e.level !is ClientLevel) return
        TurtlShellClientEvents.ENTITY_UNLOAD(e.entity)
    }

    @SubscribeEvent
    fun onConnect(e: ClientPlayerNetworkEvent.LoggingIn) {
        TurtlShellClientEvents.LEVEL_CONNECTED(Unit)
    }

    @SubscribeEvent
    fun onDisconnect(e: ClientPlayerNetworkEvent.LoggingOut) {
        TurtlShellClientEvents.LEVEL_DISCONNECTED(Unit)
    }

    @SubscribeEvent
    fun onGameStopping(e: GameShuttingDownEvent) {
        TurtlShellClientEvents.GAME_STOPPING(Unit)
    }

    @SubscribeEvent
    fun onCommandSent(e: ClientChatEvent) {
        if (!e.originalMessage.startsWith("/")) return
        TurtlShellClientEvents.COMMAND_SENT(e.originalMessage.removePrefix("/"))
    }

    @SubscribeEvent
    fun onMessageReceived(e: ClientChatReceivedEvent.System) {
        if (e.isOverlay) return
        val allowed = TurtlShellClientEvents.MESSAGE_RECEIVED(e.message)
        if (!allowed) e.isCanceled = true
    }
}