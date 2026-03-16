package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.TurtlShellCommon
import cc.turtl.turtlshell.example.core.ExampleCommon
import cc.turtl.turtlshell.impl.CommandGroupRegistrar
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object TurtlShellFabric : ModInitializer {
    override fun onInitialize() {
        ExampleCommon.init()
        TurtlShellCommon.init()

        registerCommands()
    }

    private fun registerCommands() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            CommandGroupRegistrar.registerAll(dispatcher)
        }
    }
}