package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.api.core.command.CommandRegistry
import cc.turtl.turtlshell.example.core.TurtlShellEntry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object TurtlShellFabric : ModInitializer {
    override fun onInitialize() {
        TurtlShellEntry.init()

        registerCommands()
    }

    private fun registerCommands() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            CommandRegistry.applyAll(dispatcher)
        }
    }
}