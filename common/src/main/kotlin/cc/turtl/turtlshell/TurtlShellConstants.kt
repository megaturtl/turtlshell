package cc.turtl.turtlshell

import cc.turtl.turtlshell.util.format.ColorLib
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object TurtlShellConstants {
    const val MOD_ID: String = "turtlshell"
    const val MOD_DISPLAY_NAME: String = "TurtlShell"
    const val VERSION: String = "1.0.0"
    const val AUTHOR: String = "megaturtl"

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    /**
     * Prefix for chat messages (e.g. command feedback for users)
     */
    val MESSAGE_PREFIX: MutableComponent = Component.empty()
        .append(Component.literal("[").withColor(ColorLib.DARK_GRAY.rgb))
        .append(Component.literal("\uD83D\uDEE0").withColor(ColorLib.MINT.rgb).withStyle(ChatFormatting.BOLD))
        .append(Component.literal("] ").withColor(ColorLib.DARK_GRAY.rgb))
        .withStyle(
            Style.EMPTY.withHoverEvent(
                HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    Component.literal(MOD_DISPLAY_NAME).withColor(ColorLib.MINT.rgb)
                )
            )
        )
}