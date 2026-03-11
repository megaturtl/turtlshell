package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.api.core.EnvironmentType
import cc.turtl.turtlshell.api.core.PlatformHelper
import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLEnvironment
import net.neoforged.fml.loading.FMLPaths
import java.nio.file.Path

class PlatformHelperNeoForge : PlatformHelper {
    override fun getConfigDir(): Path = FMLPaths.CONFIGDIR.get()
    override fun findPath(modId: String, path: String): Path? =
        ModList.get().getModFileById(modId)?.file?.findResource(path)

    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)
    override fun getEnvironmentType(): EnvironmentType =
        when (FMLEnvironment.dist) {
            net.neoforged.api.distmarker.Dist.CLIENT -> EnvironmentType.CLIENT
            net.neoforged.api.distmarker.Dist.DEDICATED_SERVER -> EnvironmentType.SERVER
        }
}