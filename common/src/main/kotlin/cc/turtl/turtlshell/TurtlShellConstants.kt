package cc.turtl.turtlshell

import cc.turtl.turtlshell.util.format.ColorUtils
import cc.turtl.turtlshell.util.format.ComponentUtils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.file.Path


object TurtlShellConstants {
    const val MOD_ID: String = "turtlshell"
    const val MOD_DISPLAY_NAME: String = "TurtlShell"
    const val VERSION: String = "1.0.0"
    const val AUTHOR: String = "megaturtl"

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    /**
     * Prefix for chat messages (e.g. command feedback for users)
     */
    val MESSAGE_PREFIX: Component = Component.empty()
        .append(ComponentUtils.createComponent("[", ColorUtils.DARK_GRAY.rgb))
        .append(
            Component.literal("\uD83D\uDEE0")
                .withColor(ColorUtils.MINT.rgb)
                .withStyle(ChatFormatting.BOLD)
        )
        .append(ComponentUtils.createComponent("] ", ColorUtils.DARK_GRAY.rgb))
        .withStyle { style: Style ->
            style.withHoverEvent(
                HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    ComponentUtils.createComponent(MOD_DISPLAY_NAME, ColorUtils.MINT.rgb)
                )
            )
        }
}