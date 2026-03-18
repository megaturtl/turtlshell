package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.client.keybind.KeybindRegistry
import cc.turtl.turtlshell.api.core.command.CommandRegistry
import cc.turtl.turtlshell.example.client.config.ExampleConfigClient

object ExampleCommonClient {
    fun init() {

        KeybindRegistry.registerGroup(
            category = BuildDetails.MOD_DISPLAY_NAME,
            keybinds = ExampleKeybinds.ALL
        )

        CommandRegistry.registerGroup(
            aliases = listOf(BuildDetails.MOD_ID, "ts"),
            commands = listOf(GuiCommand)
        )

        ExampleConfigClient.init()
    }
}