package cc.turtl.turtlshell.api.client.gui.widget.button

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import cc.turtl.turtlshell.api.client.gui.widget.element.SizedElement
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/** A simple labeled button for use inside a [BodyContainer]. */
class TextButton(
    label: Component,
    private val theme: GuiTheme,
    onPress: OnPress,
) : Button(0, 0, 0, FONT_HEIGHT_PX + theme.paddingMD * 2, label, onPress, DEFAULT_NARRATION), SizedElement {

    override val naturalW: Int
        get() = Minecraft.getInstance().font.width(message) + theme.paddingMD * 4

    override val naturalH: Int = FONT_HEIGHT_PX + theme.paddingMD * 2

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val bgColor = if (isHovered) theme.lightColor.rgb else theme.medColor.rgb
        guiGraphics.fill(x, y, x + width, y + height, bgColor)

        val font = Minecraft.getInstance().font
        val textX = x + (width - font.width(message)) / 2
        guiGraphics.drawVerticallyCentredText(message, textX, y, height, theme.textColor.rgb)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput)
    }
}
