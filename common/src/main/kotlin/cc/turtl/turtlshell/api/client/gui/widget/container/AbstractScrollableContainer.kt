package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_ACCENT_COLOR
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_LIGHT_COLOR
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.network.chat.Component

abstract class AbstractScrollableContainer(
    posX: Int,
    posY: Int,
    val viewWidth: Int,
    val viewHeight: Int,
) : AbstractContainerWidget(posX, posY, viewWidth, viewHeight, Component.empty()) {

    companion object {
        private const val SCROLL_AMOUNT = 10
        private const val SCROLLBAR_WIDTH = 4
    }

    private var scrollOffset: Int = 0
    private var totalContentHeight: Int = 0

    private val maxScroll: Int
        get() = maxOf(0, totalContentHeight - viewHeight)

    private val isScrollable: Boolean
        get() = maxScroll > 0

    fun updateContentHeight(height: Int) {
        totalContentHeight = height
        clampScroll()
    }

    private fun clampScroll() {
        scrollOffset = scrollOffset.coerceIn(0, maxScroll)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, deltaX: Double, deltaY: Double): Boolean {
        if (!isHovered) return false
        scrollOffset -= (deltaY * SCROLL_AMOUNT).toInt()
        clampScroll()
        return true
    }

    /**
     * Render child widgets here. Pose translation and scissoring
     * are handled by the base class.
     */
    abstract fun renderContents(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)

    final override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.enableScissor(x, y, x + viewWidth, y + viewHeight)

        guiGraphics.pose().pushPose()
        guiGraphics.pose().translate(0f, -scrollOffset.toFloat(), 0f)

        renderContents(guiGraphics, mouseX, mouseY + scrollOffset, partialTick)

        guiGraphics.pose().popPose()

        guiGraphics.disableScissor()
        renderScrollbar(guiGraphics)
    }

    private fun renderScrollbar(guiGraphics: GuiGraphics) {
        if (!isScrollable) return

        val thumbHeight = ((viewHeight.toFloat() / totalContentHeight) * viewHeight)
            .toInt()
            .coerceAtLeast(16)

        val thumbY = y + ((scrollOffset.toFloat() / maxScroll) * (viewHeight - thumbHeight)).toInt()
        val barX = x + viewWidth - SCROLLBAR_WIDTH

        guiGraphics.fill(
            barX, y,
            barX + SCROLLBAR_WIDTH, y + viewHeight,
            DEFAULT_GUI_LIGHT_COLOR.rgb
        )

        guiGraphics.fill(
            barX, thumbY,
            barX + SCROLLBAR_WIDTH, thumbY + thumbHeight,
            DEFAULT_GUI_ACCENT_COLOR.rgb
        )
    }
}