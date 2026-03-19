package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.*
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * Main content area of the GUI.
 */
class BodyWidget(
    posX: Int,
    posY: Int,
    private val panelWidth: Int,
) : AbstractContainerWidget(posX, posY, panelWidth, DEFAULT_GUI_HEADER_HEIGHT + DEFAULT_GUI_DIVIDER_WIDTH, Component.empty()) {

    private val font = Minecraft.getInstance().font

    override fun children(): List<GuiEventListener> = listOf()

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {

    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}