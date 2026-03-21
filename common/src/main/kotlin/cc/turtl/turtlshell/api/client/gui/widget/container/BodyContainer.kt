package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput

/**
 * Content area of the GUI. Sits to the right of the sidebar and fills
 * the remaining panel width.
 */
class BodyContainer(
    posX: Int,
    posY: Int,
    viewWidth: Int,
    viewHeight: Int,
    theme: GuiTheme,
) : AbstractScrollableContainer(posX, posY, viewWidth, viewHeight, theme) {

    private val childWidgets: MutableList<GuiEventListener> = mutableListOf()

    fun addContent(widget: GuiEventListener) = childWidgets.add(widget)
    fun clearContent() = childWidgets.clear()

    override fun renderContents(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        childWidgets.filterIsInstance<AbstractWidget>().forEach {
            it.render(guiGraphics, mouseX, mouseY, partialTick)
        }
    }

    override fun children(): List<GuiEventListener> = childWidgets
    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}