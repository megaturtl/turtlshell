package cc.turtl.turtlshell.api.core.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component

interface TurtlShellCommand {
    /**
     * The command name in lowercase (e.g., "info", "debug")
     */
    val name: String

    /**
     * Translatable description key for the command
     */
    val description: Component get() = Component.empty()

    /**
     * Build and return the command structure.
     * Use Commands.literal(getName()) and attach logic.
     */
    fun build(): LiteralArgumentBuilder<CommandSourceStack>
}