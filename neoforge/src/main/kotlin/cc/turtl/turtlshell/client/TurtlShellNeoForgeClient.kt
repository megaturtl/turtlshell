package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.client.keybind.KeybindRegistry
import cc.turtl.turtlshell.example.client.TurtlShellEntryClient
import cc.turtl.turtlshell.example.client.config.TurtlShellConfigClient
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
@Mod(value = BuildDetails.MOD_ID, dist = [Dist.CLIENT])
object TurtlShellNeoForgeClient {
    init {
        TurtlShellEntryClient.init()
        registerConfigScreen()
    }

    private fun registerConfigScreen() {
        ModList.get().getModContainerById(BuildDetails.MOD_ID)
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
        KeybindRegistry.all().forEach(e::register)
    }
}