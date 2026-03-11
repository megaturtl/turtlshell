package cc.turtl.turtlshell.api.client.keybind

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import org.lwjgl.glfw.GLFW

object KeybindHelper {

    fun rebind(keybind: KeyMapping, key: InputConstants.Key) {
        keybind.setKey(key)
        KeyMapping.resetMapping()
        Minecraft.getInstance().options.save()
    }

    fun isDown(keybind: KeyMapping): Boolean = isDown(keybind.key)

    fun isDown(key: InputConstants.Key): Boolean {
        if (key == InputConstants.UNKNOWN) return false
        val window = Minecraft.getInstance().window.window
        return when (key.type) {
            InputConstants.Type.MOUSE -> GLFW.glfwGetMouseButton(window, key.value) == GLFW.GLFW_PRESS
            InputConstants.Type.KEYSYM, InputConstants.Type.SCANCODE -> GLFW.glfwGetKey(window, key.value) == GLFW.GLFW_PRESS
        }
    }
}