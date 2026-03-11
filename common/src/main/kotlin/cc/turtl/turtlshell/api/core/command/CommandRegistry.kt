package cc.turtl.turtlshell.api.core.command

import cc.turtl.turtlshell.impl.CommandGroupRegistrar
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack

object CommandRegistry {

    data class CommandGroup(
        val aliases: List<String>,
        val commands: List<TurtlShellCommand>
    )

    private val groups = mutableListOf<CommandGroup>()

    /**
     * Register a group of commands under one or more root aliases.
     * Call this during mod initialization before platform registration.
     */
    fun registerGroup(aliases: List<String>, commands: List<TurtlShellCommand>) {
        require(aliases.isNotEmpty()) { "Must provide at least one alias" }
        require(commands.isNotEmpty()) { "Must provide at least one command" }
        groups += CommandGroup(aliases, commands)
    }

    /**
     * Called by platform-specific impls to wire everything into the brigadier dispatcher.
     */
    fun applyAll(dispatcher: CommandDispatcher<CommandSourceStack>) {
        groups.forEach { group ->
            CommandGroupRegistrar.register(dispatcher, group)
        }
    }
}