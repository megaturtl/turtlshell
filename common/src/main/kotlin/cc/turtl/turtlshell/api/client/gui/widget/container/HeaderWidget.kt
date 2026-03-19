package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.*
import cc.turtl.turtlshell.api.client.gui.widget.CloseButton
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * Header element of the GUI. This usually contains title text and the close button.
 */
class HeaderWidget(
    posX: Int,
    posY: Int,
    private val panelWidth: Int,
    title: Component,
    onClose: Button.OnPress,
) : AbstractContainerWidget(posX, posY, panelWidth, DEFAULT_GUI_HEADER_HEIGHT + DEFAULT_GUI_DIVIDER_WIDTH, title) {

    private val font = Minecraft.getInstance().font
    private val closeButton = CloseButton(
        posX + panelWidth - CloseButton.Companion.SIZE - DEFAULT_GUI_PADDING,
        posY + DEFAULT_GUI_PADDING,
        onClose,
    )

    override fun children(): List<GuiEventListener> = listOf(closeButton)

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {
        val titleX = x + DEFAULT_GUI_PADDING + 2
        val titleY = y + (DEFAULT_GUI_HEADER_HEIGHT - font.lineHeight) / 2 + 2

        guiGraphics.drawString(font, message, titleX, titleY, DEFAULT_GUI_TEXT_COLOR.rgb, false)

        val dividerY = y + DEFAULT_GUI_HEADER_HEIGHT
        guiGraphics.fill(x, dividerY, x + panelWidth, dividerY + DEFAULT_GUI_DIVIDER_WIDTH, DEFAULT_GUI_LIGHT_COLOR.rgb)

        closeButton.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
        closeButton.updateNarration(narrationElementOutput)
    }
}