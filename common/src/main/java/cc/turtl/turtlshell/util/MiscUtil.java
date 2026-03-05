package cc.turtl.turtlshell.util;

import cc.turtl.turtlshell.TurtlShellConstants;
import net.minecraft.resources.ResourceLocation;

public class MiscUtil {
    /**
     * Creates a ResourceLocation based on the mod id.
     */
    public static ResourceLocation modResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(TurtlShellConstants.MOD_ID, path);
    }
}
