package cc.turtl.turtlshell.fabric;

import cc.turtl.turtlshell.TurtlShellCommands;
import cc.turtl.turtlshell.platform.PlatformEventHandlers;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.network.chat.Component;

// Subscribes common handlers to platform-specific events
public class EventRegisterFabric {
    public static void register() {
        // Bridge to common event handling logic
        ClientTickEvents.END_CLIENT_TICK.register(PlatformEventHandlers::handleClientPostTick);

        ClientPlayConnectionEvents.JOIN.register((clientPacketListener, sender, minecraft)
                -> PlatformEventHandlers.handleLevelConnect());
        ClientPlayConnectionEvents.DISCONNECT.register((clientPacketListener, minecraft)
                -> PlatformEventHandlers.handleLevelDisconnect());
        ClientLifecycleEvents.CLIENT_STOPPING.register(mc
                -> PlatformEventHandlers.handleGameStopping());

        ClientSendMessageEvents.COMMAND.register(PlatformEventHandlers::handleCommandSent);

        ClientReceiveMessageEvents.MODIFY_GAME.register((message, overlay) -> {
            if (overlay) return message;
            Component potentiallyModifiedMessage = PlatformEventHandlers.handleGameMessageReceived(message);
            if (potentiallyModifiedMessage == null) return message;
            return potentiallyModifiedMessage;
        });

        // Register commands
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, conntext) -> TurtlShellCommands.register(dispatcher));
    }
}
