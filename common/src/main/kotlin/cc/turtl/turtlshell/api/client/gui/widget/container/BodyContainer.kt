package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.widget.element.LayoutAware
import cc.turtl.turtlshell.api.client.gui.widget.element.SizedElement
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput

/**
 * Content area of the GUI. Sits to the right of the sidebar and fills
 * the remaining panel width.
 *
 * Widgets can be added with [addBlock] (full-width row) or [addInline]
 * (flow-layout, wrapping to the next row when the row is full).
 * Call [finaliseLayout] (or let [cc.turtl.turtlshell.api.client.gui.screen.AbstractModalScreen]
 * do it automatically) to commit any trailing inline row.
 */
class BodyContainer(
    posX: Int,
    posY: Int,
    viewWidth: Int,
    viewHeight: Int,
    theme: GuiTheme,
    val debug: Boolean = false,
) : AbstractScrollableContainer(posX, posY, viewWidth, viewHeight, theme) {

    private data class ElementEntry(val widget: GuiEventListener, val isInline: Boolean)

    private val elementEntries: MutableList<ElementEntry> = mutableListOf()

    private var currentLayoutY: Int = 0 // Current Y for rendering the layout (accumulates)
    private var currentRowX: Int = 0
    private var currentRowH: Int = 0

    init {
        resetLayoutPos()
    }

    private fun resetLayoutPos() {
        currentLayoutY = y + theme.paddingMD
        currentRowX = x + theme.paddingMD
        currentRowH = 0
    }

    /** Adds a widget as a full-width block element on its own row. */
    fun addBlock(widget: GuiEventListener) {
        commitRow()
        val w = widget as? AbstractWidget
        if (w != null) {
            w.x = x + theme.paddingMD
            w.y = currentLayoutY
            w.width = usableW - theme.paddingMD * 2
            applyLayout(widget)
            currentLayoutY += w.height + theme.paddingMD
        } else {
            currentLayoutY += theme.paddingMD
        }
        elementEntries.add(ElementEntry(widget, false))
        updateContentHeight(currentLayoutY - y)
    }

    /** Adds a widget inline, wrapping to a new row when the current row is full. */
    fun addInline(widget: GuiEventListener) {
        val naturalWidth = (widget as? SizedElement)?.naturalW ?: (widget as? AbstractWidget)?.width ?: 0
        val clampedW = minOf(naturalWidth, usableW - theme.paddingMD * 2)

        if (currentRowX + clampedW > x + usableW - theme.paddingMD && currentRowH > 0) {
            commitRow()
        }

        val w = widget as? AbstractWidget
        if (w != null) {
            w.width = clampedW
            w.x = currentRowX
            w.y = currentLayoutY
            currentRowX += clampedW + theme.paddingMD
            currentRowH = maxOf(currentRowH, w.height)
        }

        elementEntries.add(ElementEntry(widget, true))
        updateContentHeight(currentLayoutY + currentRowH + theme.paddingMD - y)
    }

    /** Commits any trailing inline row and syncs content height. Called automatically by AbstractModalScreen. */
    fun finaliseLayout() {
        commitRow()
        updateContentHeight(currentLayoutY - y)
    }

    fun clearContent() {
        elementEntries.clear()
        resetLayoutPos()
        updateContentHeight(0)
    }

    private fun commitRow() {
        if (currentRowH == 0) return
        currentLayoutY += currentRowH + theme.paddingMD
        currentRowX = x + theme.paddingMD
        currentRowH = 0
    }

    private fun applyLayout(widget: GuiEventListener) {
        if (widget is LayoutAware) widget.applyLayout()
    }

    override fun renderContents(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        elementEntries.forEach { (widget, isInline) ->
            if (widget is AbstractWidget) {
                widget.render(guiGraphics, mouseX, mouseY, partialTick)
                if (debug) {
                    val color = if (isInline) DEBUG_COLOR_INLINE else DEBUG_COLOR_BLOCK
                    guiGraphics.renderOutline(widget.x, widget.y, widget.width, widget.height, color)
                }
            }
        }
    }

    companion object {
        private val DEBUG_COLOR_BLOCK  = 0xFF_FF4444.toInt()  // red   — block elements
        private val DEBUG_COLOR_INLINE = 0xFF_44AAFF.toInt()  // blue  — inline elements
    }

    override fun children(): List<GuiEventListener> = elementEntries.map { it.widget }
    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}
