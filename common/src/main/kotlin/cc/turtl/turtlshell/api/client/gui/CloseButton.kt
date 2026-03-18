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
        const val SIZE = 14

        private const val PADDING = 1
        private const val ICON_SIZE = SIZE - PADDING * 2

        private val COLOR_IDLE_BG = ColorLib.DARK_SLATE
        private val COLOR_IDLE_ICON = ColorLib.OFF_WHITE
        private val COLOR_HOVER_BG = ColorLib.SLATE
        private val COLOR_HOVER_ICON = ColorLib.WHITE
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgColor = if (isHovered) COLOR_HOVER_BG.rgb else COLOR_IDLE_BG.rgb
        val iconColor = if (isHovered) COLOR_HOVER_ICON else COLOR_IDLE_ICON

        context.fill(x, y, x + width, y + height, bgColor)

        context.renderTintedIcon(
            SIMPLE_ICONS.CLOSE,
            x + (width - ICON_SIZE) / 2,
            y + (height - ICON_SIZE) / 2,
            ICON_SIZE,
            color = iconColor,
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}