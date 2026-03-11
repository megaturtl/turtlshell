package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.api.client.keybind.KeybindRegistry
import cc.turtl.turtlshell.example.client.TurtlShellEntryClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper


object TurtlShellFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        EventBridgeFabricClient.register()
        TurtlShellEntryClient.init()

        registerKeybinds()
    }

    private fun registerKeybinds() {
        KeybindRegistry.all().forEach { KeyBindingHelper.registerKeyBinding(it) }
    }
}