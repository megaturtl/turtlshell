package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.client.SIMPLE_ICONS
import cc.turtl.turtlshell.api.core.format.ColorLib
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * A small close button intended for the top-right corner of a TurtlGui panel.
 *
 * @param btnX    Left edge of the button.
 * @param btnY    Top edge of the button.
 * @param onPress Called when the button is clicked.
 */
class CloseButton(
    btnX: Int,
    btnY: Int,
    onPress: OnPress,
) : Button(btnX, btnY, SIZE, SIZE, Component.empty(), onPress, DEFAULT_NARRATION) {

    companion object {
        const val SIZE = 8
        private const val PADDING = 1

        const val ICON_SIZE = SIZE - PADDING * 2

        private val COLOR_ICON_IDLE = ColorLib.WHITE
        private val COLOR_ICON_HOVER = ColorLib.OFF_WHITE
        private val COLOR_BG_IDLE = ColorLib.DARK_SLATE
        private val COLOR_BG_HOVER = ColorLib.SLATE
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgColor = if (isHovered) COLOR_BG_HOVER.rgb else COLOR_BG_IDLE.rgb
        val iconColor = if (isHovered) COLOR_ICON_HOVER else COLOR_ICON_IDLE

        context.fill(x, y, x + width, y + height, bgColor)

        context.renderTintedIcon(
            SIMPLE_ICONS.CLOSE,
            x + (width - ICON_SIZE) / 2,
            y + (height - ICON_SIZE) / 2,
            ICON_SIZE,
            color = iconColor
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}