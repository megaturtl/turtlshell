package cc.turtl.turtlshell.api.event;

public class TurtlShellEvents {
    public static final Observable<LevelConnectedEvent> LEVEL_CONNECTED = new Observable<>();
    public static final Observable<LevelDisconnectedEvent> LEVEL_DISCONNECTED = new Observable<>();
    public static final Observable<GameStoppingEvent> GAME_STOPPING = new Observable<>();

    public static final Observable<ClientPostTickEvent> CLIENT_POST_TICK = new Observable<>();

    public static final Observable<CommandSentEvent> COMMAND_SENT = new Observable<>();
    public static final Observable<MessageReceivedEvent> MESSAGE_RECEIVED = new Observable<>();
}