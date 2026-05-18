package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.TurtlShellCommon
import cc.turtl.turtlshell.client.TurtlShellNeoForgeClient
import cc.turtl.turtlshell.example.core.ExampleCommon
import cc.turtl.turtlshell.impl.CommandGroupRegistrar
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLEnvironment
import net.neoforged.neoforge.event.RegisterCommandsEvent

@EventBusSubscriber
@Mod(BuildDetails.MOD_ID)
object TurtlShellNeoForge {
    init {
        ExampleCommon.init()
        TurtlShellCommon.init()

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TurtlShellNeoForgeClient.init()
        }
    }

    @SubscribeEvent
    private fun registerServerCommands(e: RegisterCommandsEvent) {
        CommandGroupRegistrar.registerServerCommands(e.dispatcher)
    }
}