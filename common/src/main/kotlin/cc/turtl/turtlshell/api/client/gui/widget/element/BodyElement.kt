package cc.turtl.turtlshell.api.client.gui.widget.element

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.events.GuiEventListener

abstract class BodyElement(val minW: Int, val minH: Int, val inlineable: Boolean, val justify: Justify = Justify.LEFT) {
    var x = 0
    var y = 0
    var width = minW
    var height = minH
    abstract fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
}

abstract class InteractiveBodyElement(minW: Int, minH: Int, inlineable: Boolean)
    : BodyElement(minW, minH, inlineable), GuiEventListener {

    private var focused = false

    override fun isFocused(): Boolean = focused
    override fun setFocused(focused: Boolean) { this.focused = focused }

    override fun isMouseOver(mouseX: Double, mouseY: Double): Boolean =
        mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height
}

enum class Justify { LEFT, CENTER, RIGHT }