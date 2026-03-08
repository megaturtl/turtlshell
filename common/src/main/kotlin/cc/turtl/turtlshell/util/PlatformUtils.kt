package cc.turtl.turtlshell.util

import cc.turtl.turtlshell.BuildDetails
import net.minecraft.resources.ResourceLocation

/**
 * Creates a ResourceLocation based on the mod id.
 */
fun modResource(path: String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(BuildDetails.MOD_ID, path)
}