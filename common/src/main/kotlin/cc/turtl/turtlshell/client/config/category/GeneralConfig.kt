package cc.turtl.turtlshell.client.config.category

import cc.turtl.turtlshell.client.TurtlShellKeybinds
import cc.turtl.turtlshell.client.config.OptionFactory
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.config.v2.api.SerialEntry
import net.minecraft.network.chat.Component

class GeneralConfig {

    @SerialEntry
    var modDisabled: Boolean = DEFAULT_MOD_DISABLED

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
                TurtlShellKeybinds.OPEN_CONFIG
            )
        )
        .build()

    companion object {
        const val DEFAULT_MOD_DISABLED = false
    }
}