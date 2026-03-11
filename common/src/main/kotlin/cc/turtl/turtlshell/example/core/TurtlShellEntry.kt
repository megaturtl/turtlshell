package cc.turtl.turtlshell.example.core

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.command.CommandRegistry

object TurtlShellEntry {
    fun init() {

        CommandRegistry.registerGroup(
            aliases = listOf(BuildDetails.MOD_ID, "ts"),
            commands = listOf(InfoCommand)
        )

    }
}