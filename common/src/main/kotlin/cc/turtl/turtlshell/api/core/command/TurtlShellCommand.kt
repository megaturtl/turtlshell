package cc.turtl.turtlshell.api.core.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.network.chat.Component

interface TurtlShellCommand {
    val name: String
    val description: Component get() = Component.empty()

    fun build(): LiteralArgumentBuilder<*>
}