package cc.turtl.turtlshell.api.client.gui.widget.element

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/** A non-interactive text label for use inside a [cc.turtl.turtlshell.api.client.gui.widget.container.BodyContainer]. */
class LabelElement(
    private val text: Component,
    private val theme: GuiTheme,
    private val scale: Float = 1f,
) : AbstractWidget(0, 0, 0, FONT_HEIGHT_PX + theme.paddingLG * 2, Component.empty()), SizedElement {

    override val naturalW: Int
        get() = Minecraft.getInstance().font.width(text) + theme.paddingMD * 2

    override val naturalH: Int = FONT_HEIGHT_PX + theme.paddingLG * 2

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawVerticallyCentredText(text, x + theme.paddingMD, y, height, theme.textColor.rgb, scale)
    }

    override fun onClick(mouseX: Double, mouseY: Double) {}

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}
