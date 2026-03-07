package cc.turtl.turtlshell.client

import cc.turtl.turtlshell.TurtlShellConstants
import cc.turtl.turtlshell.api.TurtlShellCommand
import cc.turtl.turtlshell.client.command.InfoCommandClient
import cc.turtl.turtlshell.util.format.component.sendEmptyLine
import cc.turtl.turtlshell.util.format.component.sendPrefixed
import cc.turtl.turtlshell.util.format.component.sendSuccess
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.Minecraft

object TurtlShellClientCommands {
    private val rootAliases: List<String> = listOf(TurtlShellConstants.MOD_ID, "ts")

    private val commands: List<TurtlShellCommand> = listOf(
        InfoCommandClient()
    )

    fun <S> register(dispatcher: CommandDispatcher<S>) {
        rootAliases.forEach { alias ->
            dispatcher.register(
                LiteralArgumentBuilder.literal<S>(alias)
                    .executes { context -> showHelp(context) }
                    .apply { commands.forEach { then(it.build()) } }
            )
        }
    }

    private fun <S> showHelp(context: CommandContext<S>): Int {
        val player = Minecraft.getInstance().player ?: return 0
        val alias = context.nodes.first().node.name

        player.sendEmptyLine()
        player.sendSuccess(TurtlShellConstants.MOD_DISPLAY_NAME + " Commands")
        commands.forEach { player.sendPrefixed("  /$alias ${it.name} - ${it.description}") }
        return Command.SINGLE_SUCCESS
    }
}