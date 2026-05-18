package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.client.keybind.KeybindRegistry
import cc.turtl.turtlshell.example.client.ExampleCommonClient
import cc.turtl.turtlshell.example.client.config.ExampleConfigClient
import cc.turtl.turtlshell.impl.CommandGroupRegistrar
import net.minecraft.client.gui.screens.Screen
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
import net.neoforged.neoforge.client.gui.IConfigScreenFactory


// No @Mod here, only @EventBusSubscriber with Dist.CLIENT so KFF still wires its
// @SubscribeEvent methods to the bus.
@EventBusSubscriber(value = [Dist.CLIENT])
object TurtlShellNeoForgeClient {
    fun init() {
        ExampleCommonClient.init()
        registerConfigScreen()
    }

    private fun registerConfigScreen() {
        ModList.get().getModContainerById(BuildDetails.MOD_ID)
            .ifPresent { c: ModContainer ->
                c.registerExtensionPoint(
                    IConfigScreenFactory::class.java,
                    IConfigScreenFactory { _: ModContainer, parent: Screen ->
                        ExampleConfigClient.createScreen(parent)
                    })
            }
    }

    @SubscribeEvent
    private fun registerKeybinds(e: RegisterKeyMappingsEvent) {
        KeybindRegistry.all().forEach(e::register)
    }

    @SubscribeEvent
    private fun registerClientCommands(e: RegisterClientCommandsEvent) {
        CommandGroupRegistrar.registerClientCommands(e.dispatcher)
    }
}
