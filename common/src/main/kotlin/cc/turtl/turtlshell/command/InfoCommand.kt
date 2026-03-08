package cc.turtl.turtlshell.command

import cc.turtl.turtlshell.TurtlShellConstants
import cc.turtl.turtlshell.api.TurtlShellCommand
import cc.turtl.turtlshell.util.format.ColorLib
import cc.turtl.turtlshell.util.format.component.INDENT
import cc.turtl.turtlshell.util.format.component.MOD_PREFIX
import cc.turtl.turtlshell.util.format.component.NEW_LINE
import cc.turtl.turtlshell.util.format.component.componentOf
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component

object InfoCommand : TurtlShellCommand {
    override val name = "info"
    override val description = "Display mod info"

    override fun build(): LiteralArgumentBuilder<CommandSourceStack> =
        LiteralArgumentBuilder.literal<CommandSourceStack>(name).executes { context ->

            val message = Component.empty()
                .append(NEW_LINE)
                .append(MOD_PREFIX)
                .append(componentOf("${TurtlShellConstants.MOD_DISPLAY_NAME} Info").withColor(ColorLib.MINT.rgb))

            message.append(NEW_LINE)
                .append(MOD_PREFIX)
                .append(INDENT)
                .append("Version: ${TurtlShellConstants.VERSION}")

            message.append(NEW_LINE)
                .append(MOD_PREFIX)
                .append(INDENT)
                .append("Author: ${TurtlShellConstants.AUTHOR}")

            context.source.sendSystemMessage(message)

            Command.SINGLE_SUCCESS
        }
}