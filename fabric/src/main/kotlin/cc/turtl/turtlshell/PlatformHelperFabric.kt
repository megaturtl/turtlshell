package cc.turtl.turtlshell

import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

class PlatformHelperFabric : PlatformHelper {
    override fun getConfigDir(): Path = FabricLoader.getInstance().configDir
}