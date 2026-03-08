package cc.turtl.turtlshell

import cc.turtl.turtlshell.api.TurtlShellCommand
import cc.turtl.turtlshell.command.InfoCommand
import cc.turtl.turtlshell.util.format.ColorLib
import cc.turtl.turtlshell.util.format.component.INDENT
import cc.turtl.turtlshell.util.format.component.MOD_PREFIX
import cc.turtl.turtlshell.util.format.component.NEW_LINE
import cc.turtl.turtlshell.util.format.component.componentOf
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component

object TurtlShellCommands {
    private val rootAliases: List<String> = listOf(BuildDetails.MOD_ID, "ts")

    private val commands: List<TurtlShellCommand> = listOf(
        InfoCommand
    )

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        rootAliases.forEach { alias ->
            val root = LiteralArgumentBuilder.literal<CommandSourceStack>(alias)
                .executes { showHelp(it) }

            // Add all sub-commands to this alias
            commands.forEach { cmd ->
                root.then(cmd.build())
            }

            dispatcher.register(root)
        }
    }

    private fun showHelp(context: CommandContext<CommandSourceStack>): Int {
        // Get the actual alias used (e.g., "ts" or "turtlshell")
        val alias = context.nodes.first().node.name

        val message = Component.empty()
            .append(NEW_LINE)
            .append(MOD_PREFIX)
            .append(componentOf("${BuildDetails.MOD_DISPLAY_NAME} Commands").withColor(ColorLib.MINT.rgb))

        commands.forEach {
            message.append(NEW_LINE)
                .append(MOD_PREFIX)
                .append(INDENT)
                .append("/$alias ${it.name} - ${it.description}")
        }

        context.source.sendSystemMessage(message)

        return Command.SINGLE_SUCCESS
    }
}