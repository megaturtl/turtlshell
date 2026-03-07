package cc.turtl.turtlshell.util

import cc.turtl.turtlshell.TurtlShellConstants
import net.minecraft.resources.ResourceLocation

object MiscUtil {
    /**
     * Creates a ResourceLocation based on the mod id.
     */
    fun modResource(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(TurtlShellConstants.MOD_ID, path)
    }
}