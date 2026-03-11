package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.api.core.EnvironmentType
import cc.turtl.turtlshell.api.core.PlatformHelper
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

class PlatformHelperFabric : PlatformHelper {
    override fun getConfigDir(): Path = FabricLoader.getInstance().configDir
    override fun findPath(modId: String, path: String): Path? =
        FabricLoader.getInstance().getModContainer(modId)
            .flatMap { it.findPath(path) }
            .orElse(null)

    override fun isModLoaded(modId: String): Boolean = FabricLoader.getInstance().isModLoaded(modId)
    override fun getEnvironmentType(): EnvironmentType =
        when (FabricLoader.getInstance().environmentType) {
            net.fabricmc.api.EnvType.CLIENT -> EnvironmentType.CLIENT
            net.fabricmc.api.EnvType.SERVER -> EnvironmentType.SERVER
        }
}