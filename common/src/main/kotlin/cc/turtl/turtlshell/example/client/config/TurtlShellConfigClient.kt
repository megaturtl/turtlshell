package cc.turtl.turtlshell.example.client.config

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.Platform
import cc.turtl.turtlshell.api.client.ClientEvents
import cc.turtl.turtlshell.api.client.config.custom.KeyAdapter
import cc.turtl.turtlshell.example.client.TurtlShellKeybinds
import cc.turtl.turtlshell.example.client.config.category.GeneralConfig
import com.mojang.blaze3d.platform.InputConstants
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import dev.isxander.yacl3.gui.YACLScreen
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class TurtlShellConfigClient {

    @SerialEntry
    val general: GeneralConfig = GeneralConfig()

    companion object {
        private val HANDLER: ConfigClassHandler<TurtlShellConfigClient> =
            ConfigClassHandler.createBuilder(TurtlShellConfigClient::class.java)
                .id(ResourceLocation.fromNamespaceAndPath(BuildDetails.MOD_ID, "config"))
                .serializer { config ->
                    GsonConfigSerializerBuilder.create(config)
                        .setPath(Platform.getConfigDir().resolve("config.json"))
                        .appendGsonBuilder {
                            it.setPrettyPrinting()
                                .registerTypeHierarchyAdapter(InputConstants.Key::class.java,
                                    KeyAdapter()
                                )
                        }
                        .build()
                }
                .build()

        fun init() {
            HANDLER.load()
            ClientEvents.TICK_POST.subscribe {
                val client = Minecraft.getInstance()
                while (TurtlShellKeybinds.OPEN_CONFIG.consumeClick()) {
                    val currentScreen = client.screen
                    client.execute { client.setScreen(createScreen(currentScreen)) }
                }
            }
        }

        fun get(): TurtlShellConfigClient = HANDLER.instance()
        fun save() = HANDLER.save()

        fun createScreen(parent: Screen?): Screen =
            YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("ts.config.title"))
                .category(get().general.buildCategory())
                .save(::save)
                .build()
                .generateScreen(parent)

        /**
         * Saves config and recreates the screen to reflect changes.
         * Takes a tab index to switch back to that tab after reload.
         * This is a workaround since YACL doesn't support in-place refresh.
         */
        fun saveAndReloadScreen(parent: Screen, tabIndex: Int) {
            save()
            val newScreen = createScreen(parent) as YACLScreen
            Minecraft.getInstance().setScreen(newScreen)
            newScreen.tabNavigationBar?.let { bar ->
                if (tabIndex in 0 until bar.tabs.size) bar.selectTab(tabIndex, false)
            }
        }
    }
}