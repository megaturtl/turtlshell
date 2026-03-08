package cc.turtl.turtlshell

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.RegisterCommandsEvent

@EventBusSubscriber
@Mod(BuildDetails.MOD_ID)
object TurtlShellNeoForge {
    init {
        TurtlShell
    }

    @SubscribeEvent
    fun registerCommands(e: RegisterCommandsEvent) {
        TurtlShellCommands.register(e.dispatcher)
    }
}