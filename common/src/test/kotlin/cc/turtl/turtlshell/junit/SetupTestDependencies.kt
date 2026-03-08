package cc.turtl.turtlshell.junit

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

/**
 * Bootstraps vanilla Minecraft and sets up the Mockito extension before any tests in the
 * annotated class run. Required for any test class that directly or indirectly touches
 * Minecraft or types whose static initializers depend on MC being initialized
 * (e.g. `ItemStack`).
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@ExtendWith(BootstrapMinecraftExtension::class)
@ExtendWith(MockitoExtension::class)
annotation class SetupTestDependencies