package cc.turtl.turtlshell.api.client.gui.widget.option

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import cc.turtl.turtlshell.api.client.gui.widget.element.SizedElement
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/** A full-width toggle row with a label on the left and a sliding toggle on the right. */
class ToggleOption(
    private val label: Component,
    initialValue: Boolean,
    private val theme: GuiTheme,
    private val onChange: (Boolean) -> Unit,
) : AbstractWidget(0, 0, Int.MAX_VALUE, FONT_HEIGHT_PX + theme.paddingLG * 2, Component.empty()), SizedElement {

    var isOn: Boolean = initialValue

    override val naturalW: Int = Int.MAX_VALUE
    override val naturalH: Int = FONT_HEIGHT_PX + theme.paddingLG * 2

    private val toggleW = 20
    private val toggleH get() = FONT_HEIGHT_PX + theme.paddingMD
    private val knobSize get() = toggleH - 2

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawVerticallyCentredText(label, x + theme.paddingMD, y, height, theme.textColor.rgb)

        val trackX = x + width - toggleW - theme.paddingMD
        val trackY = y + (height - toggleH) / 2
        val trackColor = if (isOn) theme.accentColor.rgb else theme.medColor.rgb
        guiGraphics.fill(trackX, trackY, trackX + toggleW, trackY + toggleH, trackColor)

        val knobX = if (isOn) trackX + toggleW - knobSize - 1 else trackX + 1
        val knobY = trackY + 1
        guiGraphics.fill(knobX, knobY, knobX + knobSize, knobY + knobSize, theme.lightColor.rgb)
    }

    override fun onClick(mouseX: Double, mouseY: Double) {
        isOn = !isOn
        onChange(isOn)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}
