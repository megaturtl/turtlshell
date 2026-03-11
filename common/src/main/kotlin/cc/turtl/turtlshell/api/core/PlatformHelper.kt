package cc.turtl.turtlshell.api.core

import java.nio.file.Path
import java.util.*

/**
 * Provides platform-specific functions for the mod e.g. getting the config path.
 */
interface PlatformHelper {
    fun getConfigDir(): Path
    fun isModLoaded(modId: String): Boolean
}

object Platform {
    private val helper: PlatformHelper by lazy {
        ServiceLoader.load(PlatformHelper::class.java)
            .findFirst()
            .orElseThrow { IllegalStateException("No PlatformHelper found - did you register it?") }
    }

    fun getConfigDir(): Path = helper.getConfigDir()
    fun isModLoaded(modId: String): Boolean = helper.isModLoaded(modId)
}