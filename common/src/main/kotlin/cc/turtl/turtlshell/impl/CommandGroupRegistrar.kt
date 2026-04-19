package cc.turtl.turtlshell.impl

import cc.turtl.turtlshell.api.core.command.CommandRegistry
import cc.turtl.turtlshell.api.core.command.TurtlShellCommand
import cc.turtl.turtlshell.api.core.format.MessagePatterns
import cc.turtl.turtlshell.api.core.util.sendCommandFeedback
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext

/**
 * Internal logic for registering [CommandRegistry] data into live Brigadier commands.
 *
 * The unchecked cast from LiteralArgumentBuilder<*> to LiteralArgumentBuilder<S> is
 * safe because Brigadier erases the source type at runtime. The cast is intentionally
 * centralised here rather than spread across command implementations.
 */
object CommandGroupRegistrar {

    fun <S> registerClientCommands(dispatcher: CommandDispatcher<S>) {
        CommandRegistry.getClientGroups().forEach { registerGroup(dispatcher, it) }
    }

    fun <S> registerServerCommands(dispatcher: CommandDispatcher<S>) {
        CommandRegistry.getServerGroups().forEach { registerGroup(dispatcher, it) }
    }

    private fun <S> registerGroup(
        dispatcher: CommandDispatcher<S>,
        group: CommandRegistry.CommandGroup
    ) {
        group.aliases.forEach { alias ->
            val root = LiteralArgumentBuilder.literal<S>(alias)
                .executes { showHelp(it, alias, group.commands) }

            group.commands.forEach { cmd ->
                @Suppress("UNCHECKED_CAST")
                root.then(cmd.build() as LiteralArgumentBuilder<S>)
            }

            dispatcher.register(root)
        }
    }

    private fun <S> showHelp(
        context: CommandContext<S>,
        alias: String,
        commands: List<TurtlShellCommand>
    ): Int {
        context.sendCommandFeedback(
            MessagePatterns.title("ts.command.help.title", "/$alias")
                .append(MessagePatterns.helpList(alias, commands.map { it.name }))
        )
        return Command.SINGLE_SUCCESS
    }
}