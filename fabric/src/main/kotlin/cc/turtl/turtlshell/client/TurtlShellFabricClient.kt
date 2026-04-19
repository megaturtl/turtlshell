package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.api.client.keybind.KeybindRegistry
import cc.turtl.turtlshell.example.client.ExampleCommonClient
import cc.turtl.turtlshell.impl.CommandGroupRegistrar
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper


object TurtlShellFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        EventBridgeFabricClient.register()
        ExampleCommonClient.init()

        registerKeybinds()
        registerClientCommands()
    }

    private fun registerKeybinds() {
        KeybindRegistry.all().forEach { KeyBindingHelper.registerKeyBinding(it) }
    }

    private fun registerClientCommands() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            CommandGroupRegistrar.registerClientCommands(dispatcher)
        }
    }
}