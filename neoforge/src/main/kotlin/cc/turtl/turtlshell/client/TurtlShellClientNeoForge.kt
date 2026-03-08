package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.TurtlShellConstants
import cc.turtl.turtlshell.client.config.TurtlShellConfigClient
import net.minecraft.client.gui.screens.Screen
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
import net.neoforged.neoforge.client.gui.IConfigScreenFactory


@EventBusSubscriber
@Mod(value = TurtlShellConstants.MOD_ID, dist = [Dist.CLIENT])
object TurtlShellClientNeoForge {
    init {
        TurtlShellClient
        registerConfigScreen()
    }

    private fun registerConfigScreen() {
        ModList.get().getModContainerById(TurtlShellConstants.MOD_ID)
            .ifPresent { c: ModContainer ->
                c.registerExtensionPoint(
                    IConfigScreenFactory::class.java,
                    IConfigScreenFactory { container: ModContainer, parent: Screen ->
                        TurtlShellConfigClient.createScreen(parent)
                    })
            }
    }

    @SubscribeEvent
    private fun registerKeybinds(e: RegisterKeyMappingsEvent) {
        TurtlShellKeybinds.ALL.forEach(e::register)
    }
}