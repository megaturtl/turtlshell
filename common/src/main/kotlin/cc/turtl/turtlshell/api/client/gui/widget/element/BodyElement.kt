package cc.turtl.turtlshell.api.client.gui.widget.element

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvents

abstract class BodyElement(val minW: Int, val minH: Int, val inlineable: Boolean,
    /** Controls horizontal positioning within a block row. Read by [BodyContainer.positionElements], not used during rendering. */
    val justify: Justify = Justify.LEFT) {
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

    protected fun playClickSound() {
        Minecraft.getInstance().soundManager.play(
            SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f)
        )
    }
}

enum class Justify { LEFT, CENTER, RIGHT }