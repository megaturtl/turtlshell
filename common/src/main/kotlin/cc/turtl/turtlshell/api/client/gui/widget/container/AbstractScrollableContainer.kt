package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.network.chat.Component

/**
 * A scrollable container widget that clips its contents to a fixed viewport
 * and renders a vertical scrollbar when content overflows.
 *
 * Subclasses implement [renderContents] to draw their children. The usable
 * content width is [usableW], which excludes the scrollbar gutter. Call
 * [updateContentHeight] whenever the total content height changes.
 */
abstract class AbstractScrollableContainer(
    posX: Int,
    posY: Int,
    val viewportW: Int,
    val viewportH: Int,
    val theme: GuiTheme,
) : AbstractContainerWidget(posX, posY, viewportW, viewportH, Component.empty()) {

    companion object {
        const val SCROLLBAR_WIDTH = 4
        const val SCROLLBAR_GUTTER = SCROLLBAR_WIDTH + 2

        private const val SCROLL_AMOUNT = 10
        private const val HANDLE_MIN_HEIGHT = 8
    }

    /** The usable width available for content, excluding the scrollbar gutter. */
    val usableW: Int get() = viewportW - SCROLLBAR_GUTTER

    private var scrollOffset: Int = 0
    private var totalContentH: Int = 0

    private var isDraggingScrollbar = false
    private var dragHandleOffset = 0.0

    private val maxScroll: Int
        get() = maxOf(0, totalContentH - viewportH)

    private val isScrollable: Boolean
        get() = maxScroll > 0

    /** Call this whenever the total height of the content changes. */
    fun updateContentHeight(height: Int) {
        totalContentH = height
        clampScroll()
    }

    private fun clampScroll() {
        scrollOffset = scrollOffset.coerceIn(0, maxScroll)
    }

    private fun scrollbarX(): Int = x + viewportW - SCROLLBAR_WIDTH

    private fun scrollbarHandleH(): Int =
        ((viewportH.toFloat() / totalContentH) * viewportH)
            .toInt()
            .coerceAtLeast(HANDLE_MIN_HEIGHT)

    private fun scrollbarHandleY(): Int =
        y + ((scrollOffset.toFloat() / maxScroll) * (viewportH - scrollbarHandleH())).toInt()

    private fun isOverScrollbarHandle(mouseX: Double, mouseY: Double): Boolean {
        val handleY = scrollbarHandleY()
        val handleH = scrollbarHandleH()
        return mouseX >= scrollbarX()
                && mouseX <= scrollbarX() + SCROLLBAR_WIDTH
                && mouseY >= handleY
                && mouseY <= handleY + handleH
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, deltaX: Double, deltaY: Double): Boolean {
        if (!isHovered) return false
        scrollOffset -= (deltaY * SCROLL_AMOUNT).toInt()
        clampScroll()
        return true
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (isScrollable && button == 0 && mouseX >= scrollbarX() && mouseX <= scrollbarX() + SCROLLBAR_WIDTH) {
            isDraggingScrollbar = true
            if (isOverScrollbarHandle(mouseX, mouseY)) {
                dragHandleOffset = mouseY - scrollbarHandleY()
            } else {
                dragHandleOffset = scrollbarHandleH() / 2.0
                val ratio = (mouseY - y - dragHandleOffset) / (viewportH - scrollbarHandleH())
                scrollOffset = (ratio * maxScroll).toInt()
                clampScroll()
            }
            return true
        }
        return super.mouseClicked(mouseX, mouseY + scrollOffset, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (button == 0 && isDraggingScrollbar) {
            isDraggingScrollbar = false
            return true
        }
        return super.mouseReleased(mouseX, mouseY + scrollOffset, button)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): Boolean {
        if (button == 0 && isDraggingScrollbar) {
            val ratio = (mouseY - y - dragHandleOffset) / (viewportH - scrollbarHandleH())
            scrollOffset = (ratio * maxScroll).toInt()
            clampScroll()
            return true
        }
        return super.mouseDragged(mouseX, mouseY + scrollOffset, button, dragX, dragY)
    }

    /**
     * Render child widgets here. The coordinate space is pre-translated for
     * scroll, so draw as if the content starts at ([x], [y]). Lay out content
     * within [usableW] to avoid overlapping the scrollbar gutter.
     */
    abstract fun renderContents(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)

    final override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.enableScissor(x, y, x + viewportW, y + viewportH)

        guiGraphics.pose().pushPose()
        guiGraphics.pose().translate(0f, -scrollOffset.toFloat(), 0f)

        renderContents(guiGraphics, mouseX, mouseY + scrollOffset, partialTick)

        guiGraphics.pose().popPose()

        guiGraphics.disableScissor()
        renderScrollbar(guiGraphics)
    }

    private fun renderScrollbar(guiGraphics: GuiGraphics) {
        if (!isScrollable) return

        val barX = scrollbarX()
        val handleH = scrollbarHandleH()
        val handleY = scrollbarHandleY()

        guiGraphics.fill(barX, y, barX + SCROLLBAR_WIDTH, y + viewportH, theme.lightColor.rgb)
        guiGraphics.fill(barX, handleY, barX + SCROLLBAR_WIDTH, handleY + handleH, theme.accentColor.rgb)
    }
}