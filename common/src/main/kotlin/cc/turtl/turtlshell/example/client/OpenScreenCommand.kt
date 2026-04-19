package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.client.TurtlShellClientCommand
import cc.turtl.turtlshell.api.core.format.MessagePatterns
import cc.turtl.turtlshell.api.core.util.sendCommandFeedback
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object OpenScreenCommand : TurtlShellClientCommand {
    override val name = "openscreen"
    override val description: MutableComponent = Component.translatable("ts.command.openscreen.desc")

    enum class Screens(val argName: String, val factory: () -> Screen) {
        EXAMPLE("example", ::ExampleScreen);

        companion object {
            fun find(name: String) = entries.find { it.argName.equals(name, ignoreCase = true) }
            val allNames = entries.map { it.argName }
        }
    }

    override fun build(): LiteralArgumentBuilder<*> =
        LiteralArgumentBuilder.literal<Any>(name)
            .then(
                RequiredArgumentBuilder.argument<Any, String>("screen", StringArgumentType.string())
                    .suggests { _, builder -> SharedSuggestionProvider.suggest(Screens.allNames, builder) }
                    .executes { context ->
                        val screenArg = StringArgumentType.getString(context, "screen")
                        val screen = Screens.find(screenArg)

                        if (screen == null) {
                            context.sendCommandFeedback(MessagePatterns.warning("ts.command.openscreen.unknown"))
                        } else {
                            Minecraft.getInstance().tell {
                                Minecraft.getInstance().setScreen(screen.factory())
                            }
                        }

                        Command.SINGLE_SUCCESS
                    }
            )
}