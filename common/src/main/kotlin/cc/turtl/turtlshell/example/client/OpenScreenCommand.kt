package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.core.command.TurtlShellCommand
import cc.turtl.turtlshell.api.core.format.MessagePatterns
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object OpenScreenCommand : TurtlShellCommand {
    override val name = "openscreen"
    override val description: MutableComponent = Component.translatable("ts.command.openscreen.desc")

    enum class Screens(val argName: String, val factory: () -> Screen) {
        EXAMPLE("example", ::ExampleScreen);

        companion object {
            fun find(name: String) = entries.find { it.argName.equals(name, ignoreCase = true) }
            val allNames = entries.map { it.argName }
        }
    }

    override fun build(): LiteralArgumentBuilder<CommandSourceStack> =
        LiteralArgumentBuilder.literal<CommandSourceStack>(name)
            .then(RequiredArgumentBuilder.argument<CommandSourceStack, String>("screen", StringArgumentType.string())
                .suggests { _, builder -> SharedSuggestionProvider.suggest(Screens.allNames, builder) }
                .executes { context ->
                    val player = context.source.player
                        ?: run {
                            context.source.sendSystemMessage(MessagePatterns.error("ts.command.not_player"))
                            return@executes 0
                        }

                    val screenArg = StringArgumentType.getString(context, "screen")
                    val screen = Screens.find(screenArg)

                    if (screen == null) {
                        player.sendSystemMessage(MessagePatterns.warning("ts.command.openscreen.unknown"))
                    } else {
                        // Schedule onto the render thread, where setScreen is safe to call.
                        Minecraft.getInstance().execute {
                            Minecraft.getInstance().setScreen(screen.factory())
                        }
                    }

                    Command.SINGLE_SUCCESS
                }
            )
}