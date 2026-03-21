package cc.turtl.turtlshell.api.core.format

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

fun Component.withUnifont(): Component =
    (this.copy() as MutableComponent).withStyle { it.withFont(ResourceLocation.withDefaultNamespace("uniform")) }

fun Component.withBold(): Component =
    (this.copy() as MutableComponent).withStyle { it.withBold(true) }