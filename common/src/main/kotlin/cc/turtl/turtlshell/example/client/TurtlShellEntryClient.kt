package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.client.keybind.KeybindRegistry
import cc.turtl.turtlshell.example.client.config.TurtlShellConfigClient

object TurtlShellEntryClient {
    fun init() {

        KeybindRegistry.registerGroup(
            category = BuildDetails.MOD_DISPLAY_NAME,
            keybinds = TurtlShellKeybinds.ALL
        )

        TurtlShellConfigClient.init()
    }
}