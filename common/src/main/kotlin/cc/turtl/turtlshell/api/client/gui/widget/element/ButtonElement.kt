package cc.turtl.turtlshell.api.client.gui.widget.element

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

class ButtonElement(
    val label: Component,
    val theme: GuiTheme,
    minW: Int = 60,
    inlineable: Boolean = true,
    val onClick: () -> Unit = {},
) : InteractiveBodyElement(minW, MIN_H, inlineable) {

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val hovered = isMouseOver(mouseX.toDouble(), mouseY.toDouble())

        val bgColor = if (hovered) theme.buttonHoverBg.rgb else theme.buttonBg.rgb
        val textColor = theme.textColor.rgb
        guiGraphics.fill(x, y, x + width, y + height, bgColor)

        guiGraphics.drawVerticallyCentredText(
            text = label,
            x = x + theme.paddingSM,
            y = y,
            containerH = height,
            rgb = textColor,
            maxCharacterWidth = width - theme.paddingSM * 2,
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            playClickSound()
            onClick()
            return true
        }
        return false
    }

    companion object {
        const val MIN_H = 16
    }
}