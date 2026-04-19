package cc.turtl.turtlshell.api.core.util

import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component

fun <S> CommandContext<S>.sendCommandFeedback(message: Component) {
    when (val source = source) {
        is CommandSourceStack -> source.sendSystemMessage(message)
        else -> Minecraft.getInstance().player?.sendSystemMessage(message)
    }
}