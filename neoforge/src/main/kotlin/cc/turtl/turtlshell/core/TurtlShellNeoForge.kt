package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.command.CommandRegistry
import cc.turtl.turtlshell.example.core.TurtlShellEntry
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.RegisterCommandsEvent

@EventBusSubscriber
@Mod(BuildDetails.MOD_ID)
object TurtlShellNeoForge {
    init {
        TurtlShellEntry.init()
    }

    @SubscribeEvent
    private fun registerCommands(e: RegisterCommandsEvent) {
        CommandRegistry.applyAll(e.dispatcher)
    }

}