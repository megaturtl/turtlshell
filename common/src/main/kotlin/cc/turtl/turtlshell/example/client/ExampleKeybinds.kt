package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.BuildDetails
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object ExampleKeybinds {
    val OPEN_CONFIG = KeyMapping(
        "ts.key.open_config",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_F25,
        BuildDetails.MOD_DISPLAY_NAME
    )

    val ALL: List<KeyMapping> = listOf(OPEN_CONFIG)
}