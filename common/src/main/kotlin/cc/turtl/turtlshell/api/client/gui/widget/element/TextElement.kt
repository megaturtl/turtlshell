package cc.turtl.turtlshell.api.client.gui.widget.element

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import kotlin.math.ceil

class TextElement(
    private val text: Component,
    private val theme: GuiTheme,
    private val scale: Float = 1f,
) : BodyElement(
    (Minecraft.getInstance().font.width(text) * scale).toInt(),
    ceil(FONT_HEIGHT_PX * scale).toInt(),
    true
) {

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawVerticallyCentredText(
            text = text,
            x = x,
            y = y,
            containerH = height,
            rgb = theme.textColor.rgb,
            maxCharacterWidth = width,
            scale = scale,
        )
    }
}