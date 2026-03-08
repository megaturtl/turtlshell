package cc.turtl.turtlshell.client.config.custom

import com.mojang.blaze3d.platform.InputConstants
import dev.isxander.yacl3.api.Controller
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.utils.Dimension
import dev.isxander.yacl3.gui.AbstractWidget
import dev.isxander.yacl3.gui.YACLScreen
import net.minecraft.network.chat.Component

data class KeyController(val option: Option<InputConstants.Key>) : Controller<InputConstants.Key> {

    override fun option(): Option<InputConstants.Key> = option

    override fun formatValue(): Component = option.pendingValue().displayName

    override fun provideWidget(screen: YACLScreen, widgetDimension: Dimension<Int>): AbstractWidget =
        KeyWidget(this, screen, widgetDimension)
}