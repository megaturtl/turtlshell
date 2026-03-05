package cc.turtl.turtlshell.platform;

import cc.turtl.turtlshell.api.event.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class PlatformEventHandlers {
    public static void handleClientPostTick(Minecraft client) {
        ClientPostTickEvent event = new ClientPostTickEvent(client);
        TurtlShellEvents.CLIENT_POST_TICK.emit(event);
    }

    public static void handleLevelConnect() {
        LevelConnectedEvent event = new LevelConnectedEvent();
        TurtlShellEvents.LEVEL_CONNECTED.emit(event);
    }

    public static void handleLevelDisconnect() {
        LevelDisconnectedEvent event = new LevelDisconnectedEvent();
        TurtlShellEvents.LEVEL_DISCONNECTED.emit(event);
    }

    public static void handleGameStopping() {
        GameStoppingEvent event = new GameStoppingEvent();
        TurtlShellEvents.GAME_STOPPING.emit(event);
    }

    public static void handleCommandSent(String commandString) {
        CommandSentEvent event = new CommandSentEvent(commandString);
        TurtlShellEvents.COMMAND_SENT.emit(event);
    }

    public static Component handleGameMessageReceived(Component message) {
        MessageReceivedEvent event = new MessageReceivedEvent(message);
        TurtlShellEvents.MESSAGE_RECEIVED.emit(event);
        return event.getMessage();
    }
}
