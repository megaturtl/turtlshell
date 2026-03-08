package cc.turtl.turtlshell.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper


object TurtlShellClientFabric : ClientModInitializer {
    override fun onInitializeClient() {
        EventBridgeClientFabric.register()
        registerKeybinds()
        TurtlShellClient
    }

    private fun registerKeybinds() {
        TurtlShellKeybinds.ALL.forEach(KeyBindingHelper::registerKeyBinding)
    }
}