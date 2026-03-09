package cc.turtl.turtlshell

import cc.turtl.turtlshell.api.TurtlShellCommand
import cc.turtl.turtlshell.command.InfoCommand
import cc.turtl.turtlshell.util.format.component.MessagePatterns
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack

object TurtlShellCommands {
    private val rootAliases = listOf(BuildDetails.MOD_ID, "ts")

    private val commands: List<TurtlShellCommand> = listOf(
        InfoCommand
    )

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        rootAliases.forEach { alias ->
            val root = LiteralArgumentBuilder.literal<CommandSourceStack>(alias)
                .executes { showHelp(it) }

            commands.forEach { root.then(it.build()) }

            dispatcher.register(root)
        }
    }

    private fun showHelp(context: CommandContext<CommandSourceStack>): Int {
        val alias = context.nodes.first().node.name

        context.source.sendSystemMessage(
            MessagePatterns.title("ts.command.help.title")
                .append(MessagePatterns.helpList(alias, commands.map { it.name })))

        return Command.SINGLE_SUCCESS
    }
}