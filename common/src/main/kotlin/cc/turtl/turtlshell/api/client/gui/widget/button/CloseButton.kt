package cc.turtl.turtlshell.api.client.gui.widget.button

import cc.turtl.turtlshell.api.client.gui.*
import cc.turtl.turtlshell.api.client.gui.texture.IconSize
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.texture.renderSimpleIcon
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * A small close button intended for the top-right corner of a panel.
 *
 * @param btnX    Left edge of the button.
 * @param btnY    Top edge of the button.
 * @param onPress Called when the button is clicked.
 */
class CloseButton(
    btnX: Int,
    btnY: Int,
    btnSize: Int,
    onPress: OnPress,
) : Button(btnX, btnY, btnSize, btnSize, Component.empty(), onPress, DEFAULT_NARRATION) {

    companion object {
        private val BG_COLOR_IDLE = DEFAULT_GUI_DARK_COLOR
        private val BG_COLOR_HOVER = DEFAULT_GUI_MED_COLOR
        private val ICON_COLOR = DEFAULT_GUI_TEXT_COLOR
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgColor = if (isHovered) BG_COLOR_HOVER.rgb else BG_COLOR_IDLE.rgb

        context.fill(x, y, x + width, y + height, bgColor)

        context.renderSimpleIcon(
            SimpleIcons.CROSS,
            x + (width - IconSize.MD.px) / 2,
            y + (height - IconSize.MD.px) / 2,
            IconSize.MD,
            color = ICON_COLOR,
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}