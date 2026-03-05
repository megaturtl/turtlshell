package cc.turtl.turtlshell.neoforge;

import cc.turtl.turtlshell.TurtlShellCommands;
import cc.turtl.turtlshell.platform.PlatformEventHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.GameShuttingDownEvent;

// Subscribes common handlers to platform-specific events
// NeoForge events will auto register with the decorators (no need to call anything in init)
@EventBusSubscriber
public class EventRegisterNeoForge {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent e) {
        TurtlShellCommands.register(e.getDispatcher());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post e) {
        var client = Minecraft.getInstance();
        PlatformEventHandlers.handleClientPostTick(client);
    }

    @SubscribeEvent
    public static void onConnect(ClientPlayerNetworkEvent.LoggingIn e) {
        PlatformEventHandlers.handleLevelConnect();
    }

    @SubscribeEvent
    public static void onDisconnect(ClientPlayerNetworkEvent.LoggingOut e) {
        PlatformEventHandlers.handleLevelDisconnect();
    }

    @SubscribeEvent
    public static void onGameStopping(GameShuttingDownEvent e) {
        PlatformEventHandlers.handleGameStopping();
    }

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent e) {
        if (e.getOriginalMessage().startsWith("/")) {
            PlatformEventHandlers.handleCommandSent(e.getOriginalMessage().substring(1));
        }
    }

    @SubscribeEvent
    public static void onSystemMessage(ClientChatReceivedEvent.System e) {
        if (e.isOverlay()) return;
        Component result = PlatformEventHandlers.handleGameMessageReceived(e.getMessage());
        if (result != null) e.setMessage(result);
    }
}
