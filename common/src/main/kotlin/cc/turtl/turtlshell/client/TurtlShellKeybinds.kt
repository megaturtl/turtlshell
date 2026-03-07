package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.mixin.accessor.KeyMappingAccessor
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import org.lwjgl.glfw.GLFW

object TurtlShellKeybinds {
    val OPEN_CONFIG: KeyMapping = KeyMapping(
        "key.turtlshell.open_config",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_SEMICOLON,
        "key.turtlshell.categories.turtlshell"
    )

    val MUTE_ALERTS: KeyMapping = KeyMapping(
        "key.turtlshell.mute_alerts",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_M,
        "key.turtlshell.categories.turtlshell"
    )

    val ALL: List<KeyMapping> = listOf(OPEN_CONFIG, MUTE_ALERTS)

    fun rebind(keybind: KeyMapping, key: InputConstants.Key) {
        keybind.setKey(key)
        KeyMapping.resetMapping()
        Minecraft.getInstance().options.save()
    }

    fun isDown(keybind: KeyMapping): Boolean {
        return isDown((keybind as KeyMappingAccessor).`turtlshell$getKey`())
    }

    fun isDown(key: InputConstants.Key): Boolean {
        if (key == InputConstants.UNKNOWN) return false
        val window = Minecraft.getInstance().window.window
        return when (key.type) {
            InputConstants.Type.MOUSE -> GLFW.glfwGetMouseButton(window, key.value) == GLFW.GLFW_PRESS
            InputConstants.Type.KEYSYM, InputConstants.Type.SCANCODE -> GLFW.glfwGetKey(
                window,
                key.value
            ) == GLFW.GLFW_PRESS
        }
    }
}