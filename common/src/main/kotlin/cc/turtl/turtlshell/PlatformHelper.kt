package cc.turtl.turtlshell

import java.nio.file.Path
import java.util.*

/**
 * Provides platform-specific functions for the mod e.g. getting the config path.
 */
interface PlatformHelper {
    fun getConfigDir(): Path
}

object Platform {
    private val helper: PlatformHelper by lazy {
        ServiceLoader.load(PlatformHelper::class.java)
            .findFirst()
            .orElseThrow { IllegalStateException("No PlatformHelper found - did you register it?") }
    }

    fun getConfigDir(): Path = helper.getConfigDir()
}