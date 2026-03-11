package cc.turtl.turtlshell.example.core

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.command.TurtlShellCommand
import cc.turtl.turtlshell.api.core.format.MessagePatterns
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object InfoCommand : TurtlShellCommand {
    override val name = "info"
    override val description: MutableComponent = Component.translatable("ts.command.info.desc")

    override fun build(): LiteralArgumentBuilder<CommandSourceStack> =
        LiteralArgumentBuilder.literal<CommandSourceStack>(name).executes { context ->

            context.source.sendSystemMessage(
                MessagePatterns.title("ts.command.info.title")
                    .append(MessagePatterns.fieldLine("ts.command.info.author.label", BuildDetails.MOD_AUTHOR))
                    .append(MessagePatterns.fieldLine("ts.command.info.version.label", BuildDetails.MOD_VERSION))
                    .append(MessagePatterns.fieldLine("ts.command.info.author.label", BuildDetails.smallCommitHash()))
                    .append(MessagePatterns.fieldLine("ts.command.info.branch.label", BuildDetails.BRANCH))
            )

            Command.SINGLE_SUCCESS
        }
}