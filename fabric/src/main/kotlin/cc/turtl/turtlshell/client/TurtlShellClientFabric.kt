package cc.turtl.turtlshell.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper


object TurtlShellClientFabric : ClientModInitializer {
    override fun onInitializeClient() {
        EventBridgeClientFabric.register()
        registerKeybinds()
        registerCommands()
        TurtlShellClient
    }

    private fun registerKeybinds() {
        TurtlShellKeybinds.ALL.forEach(KeyBindingHelper::registerKeyBinding)
    }

    private fun registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            TurtlShellClientCommands.register(dispatcher)
        }
    }
}