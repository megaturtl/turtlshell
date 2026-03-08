package cc.turtl.turtlshell.api

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack

interface TurtlShellCommand {
    /**
     * The command name in lowercase (e.g., "info", "debug")
     */
    val name: String

    /**
     * Short description for help text
     */
    val description: String get() = ""

    /**
     * Build and return the command structure.
     * Use Commands.literal(getName()) and attach logic.
     */
    fun build(): LiteralArgumentBuilder<CommandSourceStack>
}