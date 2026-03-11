package cc.turtl.turtlshell.api.client.config.custom

import dev.isxander.yacl3.api.Controller
import dev.isxander.yacl3.api.utils.Dimension
import dev.isxander.yacl3.gui.AbstractWidget
import dev.isxander.yacl3.gui.YACLScreen
import net.minecraft.network.chat.Component
import java.util.function.BiConsumer

class HoldToConfirmController(
    private val option: HoldToConfirmButton,
    private val text: Component
) : Controller<BiConsumer<YACLScreen, HoldToConfirmButton>> {

    override fun option(): HoldToConfirmButton = option

    override fun formatValue(): Component = text

    override fun provideWidget(screen: YACLScreen, widgetDimension: Dimension<Int>): AbstractWidget =
        HoldToConfirmWidget(this, screen, widgetDimension)
}