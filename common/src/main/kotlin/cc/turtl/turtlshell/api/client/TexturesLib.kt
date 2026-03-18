package cc.turtl.turtlshell.api.client

import cc.turtl.turtlshell.BuildDetails
import net.minecraft.resources.ResourceLocation

/**
 * Simple single-colored UI Icons
 */
object SIMPLE_ICONS {
    const val ICON_SIZE = 64

    val CLOSE: ResourceLocation = internalResource("textures/gui/icon/simple/simple_icon_close.png")
}

private fun internalResource(path: String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(BuildDetails.MOD_ID, path)
}