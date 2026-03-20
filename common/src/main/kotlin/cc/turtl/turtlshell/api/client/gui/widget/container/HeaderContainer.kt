package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_PADDING
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_TEXT_COLOR
import cc.turtl.turtlshell.api.client.gui.widget.button.CloseButton
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
class HeaderContainer(
    headerX: Int,
    headerY: Int,
    headerW: Int,
    headerH: Int,
    title: Component,
    onClose: Button.OnPress,
) : AbstractContainerWidget(
    headerX,
    headerY, headerW, headerH, title
) {

    private val font = Minecraft.getInstance().font

    val btnSize = height - DEFAULT_GUI_PADDING * 2
    val btnX = x + width - btnSize - DEFAULT_GUI_PADDING
    val btnY = y + DEFAULT_GUI_PADDING
    private val closeButton = CloseButton(btnX, btnY, btnSize, onClose)

    override fun children(): List<GuiEventListener> = listOf(closeButton)

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {
        val titleX = x + DEFAULT_GUI_PADDING + 2
        val titleY = y + (height - font.lineHeight) / 2 + 2

        guiGraphics.drawString(font, message, titleX, titleY, DEFAULT_GUI_TEXT_COLOR.rgb, false)

        closeButton.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
        closeButton.updateNarration(narrationElementOutput)
    }
}