package cc.turtl.turtlshell.junit

import net.minecraft.SharedConstants
import net.minecraft.server.Bootstrap
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * JUnit extension that bootstraps vanilla Minecraft before any tests in the class run.
 *
 *
 * Use via [SetupTestDependencies] - do not reference this class directly.
 */
class BootstrapMinecraftExtension : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        SharedConstants.tryDetectVersion()
        Bootstrap.bootStrap()
    }
}