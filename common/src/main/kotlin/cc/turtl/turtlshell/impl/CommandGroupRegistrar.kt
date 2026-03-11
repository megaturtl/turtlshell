package cc.turtl.turtlshell.impl

import cc.turtl.turtlshell.api.core.command.CommandRegistry
import cc.turtl.turtlshell.api.core.command.TurtlShellCommand
import cc.turtl.turtlshell.api.core.format.MessagePatterns
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack

internal object CommandGroupRegistrar {

    fun register(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        group: CommandRegistry.CommandGroup
    ) {
        group.aliases.forEach { alias ->
            val root = LiteralArgumentBuilder.literal<CommandSourceStack>(alias)
                .executes { showHelp(it, alias, group.commands) }

            group.commands.forEach { root.then(it.build()) }

            dispatcher.register(root)
        }
    }

    private fun showHelp(
        context: CommandContext<CommandSourceStack>,
        alias: String,
        commands: List<TurtlShellCommand>
    ): Int {
        context.source.sendSystemMessage(
            MessagePatterns.title("ts.command.help.title", "/$alias")
                .append(MessagePatterns.helpList(alias, commands.map { it.name }))
        )
        return Command.SINGLE_SUCCESS
    }
}