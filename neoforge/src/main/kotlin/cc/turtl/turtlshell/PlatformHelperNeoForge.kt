package cc.turtl.turtlshell

import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLPaths
import java.nio.file.Path

class PlatformHelperNeoForge : PlatformHelper {
    override fun getConfigDir(): Path = FMLPaths.CONFIGDIR.get()
    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId);
}