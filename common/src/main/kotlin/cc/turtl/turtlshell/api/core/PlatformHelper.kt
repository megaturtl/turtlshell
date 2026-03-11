package cc.turtl.turtlshell.api.core

import java.nio.file.Path
import java.util.*

/**
 * Provides platform-specific functions for the mod e.g. getting the config path.
 */
interface PlatformHelper {
    fun getConfigDir(): Path
    fun findPath(modId: String, path: String): Path?
    fun isModLoaded(modId: String): Boolean
    fun getEnvironmentType(): EnvironmentType
}

object Platform {
    private val helper: PlatformHelper by lazy {
        ServiceLoader.load(PlatformHelper::class.java)
            .findFirst()
            .orElseThrow { IllegalStateException("No PlatformHelper found - did you register it?") }
    }

    val isClient by lazy { getEnvironmentType() == EnvironmentType.CLIENT }
    val isServer get() = !isClient

    fun getConfigDir(): Path = helper.getConfigDir()
    fun isModLoaded(modId: String): Boolean = helper.isModLoaded(modId)
    fun getEnvironmentType(): EnvironmentType = helper.getEnvironmentType()
    fun findPath(modId: String, path: String): Path? = helper.findPath(modId, path)
}

enum class EnvironmentType {
    CLIENT, SERVER
}