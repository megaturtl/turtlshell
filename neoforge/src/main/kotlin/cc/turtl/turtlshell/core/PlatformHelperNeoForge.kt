package cc.turtl.turtlshell.core

import cc.turtl.turtlshell.api.core.PlatformHelper
import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLPaths
import java.nio.file.Path

class PlatformHelperNeoForge : PlatformHelper {
    override fun getConfigDir(): Path = FMLPaths.CONFIGDIR.get()
    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)
}