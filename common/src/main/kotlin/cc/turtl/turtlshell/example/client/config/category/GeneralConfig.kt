package cc.turtl.turtlshell.example.client.config.category

import cc.turtl.turtlshell.api.client.config.OptionFactory
import cc.turtl.turtlshell.example.client.ExampleKeybinds
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.config.v2.api.SerialEntry
import net.minecraft.network.chat.Component
import org.lwjgl.util.freetype.FT_Var_Axis.DEF

class GeneralConfig {

    @SerialEntry
    var modDisabled: Boolean = DEFAULT_MOD_DISABLED

    @SerialEntry
    var debug: Boolean = DEFAULT_DEBUG

    fun buildCategory(): ConfigCategory = ConfigCategory.createBuilder()
        .name(Component.translatable("ts.config.category.general"))
        .option(
            OptionFactory.toggleOnOff(
                "ts.config.general.mod_disabled",
                DEFAULT_MOD_DISABLED,
                { modDisabled },
                { modDisabled = it })
        )
        .option(
            OptionFactory.keyMappingPicker(
                "ts.config.general.open_config_keybind",
                ExampleKeybinds.OPEN_CONFIG
            )
        )
        .option(
            OptionFactory.toggleOnOff(
                "ts.config.general.debug_mode",
                DEFAULT_DEBUG,
                { debug },
                { debug = it }
            )
        )
        .build()

    companion object {
        const val DEFAULT_MOD_DISABLED = false
        const val DEFAULT_DEBUG = false
    }
}