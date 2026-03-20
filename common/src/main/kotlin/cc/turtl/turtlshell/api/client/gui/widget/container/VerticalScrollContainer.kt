package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_LIGHT_COLOR
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_PADDING
import cc.turtl.turtlshell.api.core.format.argb
import cc.turtl.turtlshell.api.core.format.opacity
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * A scrollable container that clips its children to its view bounds and shows
 * a scrollbar if content overflows vertically.
 */
class VerticalScrollContainer(
    posX: Int,
    posY: Int,
    private val viewWidth: Int,
    private val viewHeight: Int,
) : AbstractContainerWidget(posX, posY, viewWidth, viewHeight, Component.empty()) {

    companion object {
        private const val SCROLL_AMOUNT = 10
        private const val SCROLLBAR_WIDTH = 4
    }

    private var totalContentHeight: Int = 0
    private var scrollOffset: Int = 0
    private val childWidgets: MutableList<GuiEventListener> = mutableListOf()

    // region -- Child management

    fun addChild(widget: GuiEventListener) {
        childWidgets.add(widget)
    }

    fun clearChildren() {
        childWidgets.clear()
    }

    // endregion

    // region -- Scrolling

    private val maxScroll: Int
        get() = maxOf(0, totalContentHeight - viewHeight)

    private val isScrollable: Boolean
        get() = maxScroll > 0

    fun setTotalContentHeight(height: Int) {
        totalContentHeight = height
        clampScroll()
    }

    private fun clampScroll() {
        scrollOffset = scrollOffset.coerceIn(0, maxScroll)
    }

    override fun mouseScrolled(
        mouseX: Double,
        mouseY: Double,
        deltaX: Double,
        deltaY: Double,
    ): Boolean {
        if (!isHovered) return false
        scrollOffset -= (deltaY * SCROLL_AMOUNT).toInt()
        clampScroll()
        return true
    }

    // endregion

    // region -- Rendering

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {
        guiGraphics.enableScissor(x, y, x + viewWidth, y + viewHeight)

        val adjustedMouseY = mouseY + scrollOffset

        childWidgets.forEach { child ->
            if (child is AbstractWidget) {
                child.render(guiGraphics, mouseX, adjustedMouseY, partialTick)
            }
        }

        guiGraphics.disableScissor()

        if (isScrollable) {
            renderScrollbar(guiGraphics)
        }
    }

    private fun renderScrollbar(guiGraphics: GuiGraphics) {
        val thumbHeight = ((viewHeight.toFloat() / totalContentHeight) * viewHeight)
            .toInt()
            .coerceAtLeast(16)

        val thumbY = y + ((scrollOffset.toFloat() / maxScroll) * (viewHeight - thumbHeight)).toInt()
        val barX = x + viewWidth - SCROLLBAR_WIDTH - DEFAULT_GUI_PADDING

        // Track
        guiGraphics.fill(
            barX, y,
            barX + SCROLLBAR_WIDTH, y + viewHeight,
            DEFAULT_GUI_LIGHT_COLOR.opacity(0.8F).argb
        )

        // Thumb
        guiGraphics.fill(
            barX, thumbY,
            barX + SCROLLBAR_WIDTH, thumbY + thumbHeight,
            DEFAULT_GUI_LIGHT_COLOR.rgb
        )
    }

    // endregion

    override fun children(): List<GuiEventListener> = childWidgets
    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}