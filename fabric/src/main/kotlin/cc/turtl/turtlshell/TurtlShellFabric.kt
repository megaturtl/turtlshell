package cc.turtl.turtlshell

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object TurtlShellFabric : ModInitializer {
    override fun onInitialize() {
        registerCommands()
        TurtlShell
    }

    private fun registerCommands() {
        CommandRegistrationCallback.EVENT.register { commandDispatcher, _, _ ->
            TurtlShellCommands.register(commandDispatcher)
        }
    }
}