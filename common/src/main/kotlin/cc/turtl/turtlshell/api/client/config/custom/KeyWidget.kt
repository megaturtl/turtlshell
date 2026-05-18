package cc.turtl.turtlshell.api.client.config.custom

import com.mojang.blaze3d.platform.InputConstants
import dev.isxander.yacl3.api.utils.Dimension
import dev.isxander.yacl3.gui.YACLScreen
import dev.isxander.yacl3.gui.controllers.ControllerWidget
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import org.lwjgl.glfw.GLFW

class KeyWidget(
    controller: KeyController,
    screen: YACLScreen,
    dim: Dimension<Int>
) : ControllerWidget<KeyController>(controller, screen, dim) {

    private var listening = false

    override fun getValueText(): Component =
        if (listening) Component.translatable("ts.config.keybind.press_key_prompt")
        else control.option().pendingValue().displayName

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(graphics, mouseX, mouseY, delta)
        if (!listening) return

        val window = Minecraft.getInstance().window.window
        for (button in POLLED_BUTTONS) {
            if (GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS) {
                control.option().requestSet(InputConstants.Type.MOUSE.getOrCreate(button))
                playDownSound()
                listening = false
                return
            }
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (!isMouseOver(mouseX, mouseY) || !isAvailable) return false

        if (listening) {
            control.option().requestSet(InputConstants.Type.MOUSE.getOrCreate(button))
            listening = false
            return true
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            playDownSound()
            listening = true
            return true
        }

        return false
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (!listening) return false

        control.option().requestSet(
            if (keyCode == InputConstants.KEY_ESCAPE) InputConstants.UNKNOWN
            else InputConstants.getKey(keyCode, scanCode)
        )
        listening = false
        return true
    }

    override fun unfocus() {
        super.unfocus()
        listening = false
    }

    override fun getHoveredControlWidth(): Int = unhoveredControlWidth

    override fun canReset(): Boolean = true

    companion object {
        private val POLLED_BUTTONS = intArrayOf(
            GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
            GLFW.GLFW_MOUSE_BUTTON_4,
            GLFW.GLFW_MOUSE_BUTTON_5,
        )
    }
}