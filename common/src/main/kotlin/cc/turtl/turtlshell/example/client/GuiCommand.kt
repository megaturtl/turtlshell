package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.client.gui.BaseGui
import cc.turtl.turtlshell.api.core.command.TurtlShellCommand
import cc.turtl.turtlshell.api.core.format.MessagePatterns
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object GuiCommand : TurtlShellCommand {
    override val name = "gui"
    override val description: MutableComponent = Component.translatable("ts.command.gui.desc")

    enum class Guis(val argName: String, val opener: () -> Unit) {
        BASE("base", { BaseGui.open() });

        companion object {
            fun find(name: String) = entries.find { it.argName.equals(name, ignoreCase = true) }
            val allNames = entries.map { it.argName }
        }
    }

    override fun build(): LiteralArgumentBuilder<CommandSourceStack> =
        LiteralArgumentBuilder.literal<CommandSourceStack>(name)
            .then(RequiredArgumentBuilder.argument<CommandSourceStack, String>("type", StringArgumentType.string())
                .suggests { _, builder -> SharedSuggestionProvider.suggest(Guis.allNames, builder) }
                .executes { context ->
                    val typeArg = StringArgumentType.getString(context, "type")
                    val player = context.source.player

                    if (player == null) {
                        context.source.sendSystemMessage(MessagePatterns.error("ts.command.not_player"))
                        return@executes 0
                    }

                    val gui = Guis.find(typeArg)
                    if (gui != null) {
                        Minecraft.getInstance().execute { gui.opener() }
                    } else {
                        player.sendSystemMessage(MessagePatterns.warning("ts.command.gui.unknown"))
                    }

                    Command.SINGLE_SUCCESS
                }
            )
}