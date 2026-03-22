package cc.turtl.turtlshell.api.client.gui.widget.option

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import cc.turtl.turtlshell.api.client.gui.widget.element.LayoutAware
import cc.turtl.turtlshell.api.client.gui.widget.element.SizedElement
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * A full-width option row with a label on the left and a text input field on the right.
 *
 * Implements [LayoutAware] so the internal [EditBox] is repositioned after the layout
 * engine assigns x/y/width. Implements [AbstractContainerWidget] so keyboard events
 * route down to the [EditBox] via the focus chain.
 */
class TextInputOption(
    private val label: Component,
    initialValue: String,
    private val theme: GuiTheme,
    private val onChange: (String) -> Unit,
) : AbstractContainerWidget(0, 0, Int.MAX_VALUE, FONT_HEIGHT_PX + theme.paddingLG * 2, Component.empty()),
    LayoutAware, SizedElement {

    override val naturalW: Int = Int.MAX_VALUE
    override val naturalH: Int = FONT_HEIGHT_PX + theme.paddingLG * 2

    private val font = Minecraft.getInstance().font
    private val fieldH = FONT_HEIGHT_PX + theme.paddingSM * 2

    private val editBox = EditBox(font, 0, 0, 50, fieldH, Component.empty()).apply {
        isBordered = false
        this.setMaxLength(256)
        value = initialValue
        setResponder(onChange)
    }

    override fun applyLayout() {
        val fieldW = width / 3
        editBox.x = x + width - fieldW - theme.paddingMD
        editBox.y = y + (height - fieldH) / 2
        editBox.width = fieldW
        editBox.height = fieldH
    }

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawVerticallyCentredText(label, x + theme.paddingMD, y, height, theme.textColor.rgb)

        val bgX = editBox.x - theme.paddingSM
        val bgY = editBox.y - theme.paddingSM
        val bgRight = editBox.x + editBox.width + theme.paddingSM
        val bgBottom = editBox.y + editBox.height + theme.paddingSM
        guiGraphics.fill(bgX, bgY, bgRight, bgBottom, theme.medColor.rgb)

        editBox.render(guiGraphics, mouseX, mouseY, 0f)
    }

    override fun children(): List<GuiEventListener> = listOf(editBox)

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
        editBox.updateNarration(narrationElementOutput)
    }
}
