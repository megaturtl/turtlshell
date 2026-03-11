package cc.turtl.turtlshell.api.client.config.custom

import cc.turtl.turtlshell.api.core.format.ColorLib
import cc.turtl.turtlshell.api.core.util.toArgb
import com.mojang.blaze3d.platform.InputConstants
import dev.isxander.yacl3.api.utils.Dimension
import dev.isxander.yacl3.gui.YACLScreen
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

class HoldToConfirmWidget(
    controller: HoldToConfirmController,
    screen: YACLScreen,
    dim: Dimension<Int>
) : dev.isxander.yacl3.gui.controllers.ControllerWidget<HoldToConfirmController>(controller, screen, dim) {

    private val heldKeys = mutableSetOf<Int>()
    private val holdTimeTicks: Int = controller.option().holdTimeTicks
    private var progressTicks = 0f

    override fun getValueText(): Component =
        if (heldKeys.isNotEmpty() && progressTicks > 0) control.option().holdingText
        else control.option().buttonText

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(graphics, mouseX, mouseY, delta)

        if (progressTicks > 0f) {
            val barWidth = ((progressTicks / holdTimeTicks) * (dimension.width() - 2)).toInt()
            graphics.fill(
                dimension.x() + 1,
                dimension.y() + 1,
                dimension.x() + 1 + barWidth,
                dimension.yLimit() - 1,
                PROGRESS_COLOR
            )
        }

        if (heldKeys.isNotEmpty() && isAvailable) {
            progressTicks = minOf(holdTimeTicks.toFloat(), progressTicks + delta)
            if (progressTicks >= holdTimeTicks) {
                executeAction()
                progressTicks = 0f
                heldKeys.clear()
            }
        } else {
            progressTicks = maxOf(0f, progressTicks - REGRESSION_MULTIPLIER * delta)
        }

        if (!isAvailable) heldKeys.clear()
        if (-1 in heldKeys && !isMouseOver(mouseX.toDouble(), mouseY.toDouble())) heldKeys.remove(-1)
    }

    private fun executeAction() {
        playDownSound()
        control.option().action.accept(screen, control.option())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (isMouseOver(mouseX, mouseY) && isAvailable) {
            playDownSound()
            heldKeys.add(-1)
            return true
        }
        return false
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        heldKeys.remove(-1)
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        if (isAvailable && !isMouseOver(mouseX, mouseY)) heldKeys.remove(-1)
        super.mouseMoved(mouseX, mouseY)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (!focused) return false
        if (isActivationKey(keyCode)) {
            if (heldKeys.isEmpty()) playDownSound()
            heldKeys.add(keyCode)
            return true
        }
        return false
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (isActivationKey(keyCode)) heldKeys.remove(keyCode)
        return super.keyReleased(keyCode, scanCode, modifiers)
    }

    override fun unfocus() {
        super.unfocus()
        heldKeys.clear()
    }

    override fun getHoveredControlWidth(): Int = unhoveredControlWidth

    override fun canReset(): Boolean = false

    companion object {
        private const val REGRESSION_MULTIPLIER = 2f
        private val PROGRESS_COLOR = toArgb(ColorLib.RED.rgb, 0.75f)

        private fun isActivationKey(keyCode: Int) =
            keyCode == InputConstants.KEY_RETURN ||
                    keyCode == InputConstants.KEY_SPACE ||
                    keyCode == InputConstants.KEY_NUMPADENTER
    }
}