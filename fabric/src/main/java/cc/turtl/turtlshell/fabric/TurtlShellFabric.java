package cc.turtl.turtlshell.fabric;

import cc.turtl.turtlshell.TurtlShell;
import net.fabricmc.api.ClientModInitializer;

public final class TurtlShellFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EventRegisterFabric.register();
        TurtlShell.initClient();
    }
}