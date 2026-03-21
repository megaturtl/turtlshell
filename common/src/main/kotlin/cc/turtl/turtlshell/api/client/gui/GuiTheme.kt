package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.core.format.ColorLib
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import java.awt.Color

/**
 * Defines the visual style for a GUI screen and its child widgets.
 *
 * Example:
 * ```
 * val newTheme = GuiStyle(accentColor = ColorLib.RED) // Keep defaults but use red as the accent.
 * ```
 */
data class GuiTheme(
    val accentColor: Color = ColorLib.MINT,
    val lightColor: Color = ColorLib.LIGHT_SLATE,
    val medColor: Color = ColorLib.SLATE,
    val darkColor: Color = ColorLib.DARK_SLATE,
    val textColor: Color = ColorLib.WHITE,

    val dividerWidth: Int = 1,

    // These are per side. So 1 padding above and below for example.
    val paddingXS: Int = 1,
    val paddingSM: Int = 2,
    val paddingMD: Int = 4,
    val paddingLG: Int = 8,
) {
    companion object {
        val DEFAULT = GuiTheme()
    }
}