package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.widget.element.BodyElement
import cc.turtl.turtlshell.api.core.format.ColorLib
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput

class BodyContainer(
    posX: Int,
    posY: Int,
    viewWidth: Int,
    viewHeight: Int,
    theme: GuiTheme,
    val debug: Boolean = false,
) : AbstractScrollableContainer(posX, posY, viewWidth, viewHeight, theme) {

    val paddedX: Int = x + theme.paddingMD
    val paddedY: Int = y + theme.paddingMD
    val paddedW: Int = usableW - theme.paddingMD * 2

    val minElementH: Int = 7 + theme.paddingSM * 2

    private data class ElementEntry(val element: BodyElement, val inline: Boolean)
    private val elementEntries: MutableList<ElementEntry> = mutableListOf()

    fun addBlock(element: BodyElement) {
        elementEntries.add(ElementEntry(element, false))
    }

    fun addInline(element: BodyElement) {
        if (!element.inlineable) {
            addBlock(element)
            return
        }
        elementEntries.add(ElementEntry(element, true))
    }

    fun clearElements() {
        elementEntries.clear()
        updateContentHeight(0)
    }

    /**
     * Walks all elements and computes their positions from scratch.
     * Call this after adding elements or when the layout needs to refresh.
     */
    fun positionElements() {
        var curY = paddedY
        var curRowX = paddedX
        var curRowH = 0

        fun commitRow() {
            if (curRowH == 0) return
            curY += curRowH + theme.paddingMD
            curRowX = paddedX
            curRowH = 0
        }

        for ((element, inline) in elementEntries) {
            if (!inline) {
                commitRow()
                element.x = paddedX
                element.y = curY
                element.width = paddedW
                element.height = maxOf(element.height, minElementH)
                curY += element.height + theme.paddingMD
            } else {
                val clampedW = minOf(element.minW, paddedW)
                if (curRowX + clampedW > paddedX + paddedW && curRowH > 0) {
                    commitRow()
                }
                element.x = curRowX
                element.y = curY
                element.width = clampedW
                element.height = maxOf(element.height, minElementH)
                curRowX += clampedW + theme.paddingMD
                curRowH = maxOf(curRowH, element.height)
            }
        }

        commitRow() // flush any trailing inline row
        updateContentHeight(curY - y)
    }

    override fun renderContents(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        elementEntries.forEach { (element, inline) ->
            element.render(guiGraphics, mouseX, mouseY, partialTick)
            if (debug) {
                val color = if (inline) DEBUG_COLOR_INLINE else DEBUG_COLOR_BLOCK
                guiGraphics.renderOutline(element.x, element.y, element.width, element.height, color)
            }
        }
    }

    companion object {
        private val DEBUG_COLOR_BLOCK = ColorLib.RED.rgb
        private val DEBUG_COLOR_INLINE = ColorLib.BLUE.rgb
    }

    override fun children(): List<GuiEventListener> =
        elementEntries.mapNotNull { it.element as? GuiEventListener }
    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}