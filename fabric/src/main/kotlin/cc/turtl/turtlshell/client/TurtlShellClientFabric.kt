package cc.turtl.turtlshell.client

import net.fabricmc.api.ClientModInitializer

object TurtlShellFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        TurtlShellClient
    }
}