package cc.turtl.turtlshell.client.command

import cc.turtl.turtlshell.TurtlShellConstants
import cc.turtl.turtlshell.api.TurtlShellCommand
import cc.turtl.turtlshell.util.format.component.sendEmptyLine
import cc.turtl.turtlshell.util.format.component.sendLabelled
import cc.turtl.turtlshell.util.format.component.sendSuccess
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.client.Minecraft

class InfoCommandClient : TurtlShellCommand {
    override val name = "info"
    override val description = "Display mod info"

    override fun <S> build(): LiteralArgumentBuilder<S> =
        LiteralArgumentBuilder.literal<S>(name).executes {
            val player = Minecraft.getInstance().player ?: return@executes 0
            player.sendEmptyLine()
            player.sendSuccess(TurtlShellConstants.MOD_DISPLAY_NAME + " Info")
            player.sendLabelled("  Version", TurtlShellConstants.VERSION)
            player.sendLabelled("  Author", TurtlShellConstants.AUTHOR)
            Command.SINGLE_SUCCESS
        }
}